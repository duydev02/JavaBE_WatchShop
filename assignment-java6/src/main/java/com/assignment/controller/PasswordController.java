package com.assignment.controller;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.assignment.entity.Users;
import com.assignment.service.UsersService;
import com.assignment.util.SessionUtil;
import com.assignment.util.UserNotFoundException;

import net.bytebuddy.utility.RandomString;

@Controller
public class PasswordController {

	@Autowired
	HttpServletRequest request;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private UsersService userService;

	@GetMapping("/forgot-password")
	public String doGetForgotPassword(Model model) {
		model.addAttribute("titleMain", "Forgot Password");
		return "user/forgot-password";
	}

	@GetMapping("/reset-password")
	public String doGetResetPassword(@RequestParam("token") Optional<String> token, @RequestParam("change") Optional<String> change,
			Model model) {
		model.addAttribute("titleMain", "Reset Password");
		String o = change.orElse(null);
		if (o != null) {
			model.addAttribute("change", "changed");
			return "user/reset-password";
		}
		String y = token.orElse(null);
		if (y != null) {
			Users user = userService.findByResetPassword(y);
			if (user != null) {
				model.addAttribute("token", y);
				return "user/reset-password";
			}
		}
		return "redirect:/login";
	}
	
	@GetMapping("/active-account")
	public String doGetActive(@RequestParam("token") Optional<String> token) throws Exception {
		String y = token.orElse(null);
		if (y != null) {
			Users user = userService.findByActive(y);
			if (user != null) {
				userService.updateActive(y);
				return "redirect:/index";
			}
		}
		return "redirect:/index";
	}

	@PostMapping("/forgot-password")
	public String doPostForgotPassword(@RequestParam("email") String email, Model model) throws Exception {
		String token = RandomString.make(30);
		try {
			userService.updateResetPassword(token, email);
			String username = userService.findByEmail(email).getUsername();
			String resetPasswordLink = SessionUtil.getSiteURL(request) + "/reset-password?token=" + token;
			sendEmail(email, username, resetPasswordLink);
			model.addAttribute("message", "We have sent a reset password link to your email. Please check your email.");
		} catch (UserNotFoundException e) {
			model.addAttribute("error", e.getMessage());
		} catch (UnsupportedEncodingException | MessagingException e) {
			model.addAttribute("error", "Error while sending email.");
		}
		model.addAttribute("titleMain", "Forgot Password");
		return "user/forgot-password";
	}

	@PostMapping("/reset-password")
	public String doPostResetPassword(@RequestParam("token") String token, @RequestParam("password") String password,
			Model model) {
		model.addAttribute("titleMain", "Reset Password");

		Users user = userService.findByResetPassword(token);
		if (user == null) {
			model.addAttribute("titleMain", "Login");
			return "redirect:/login";
		} else {
			userService.updatePassword(user, password);
			return "redirect:/reset-password?change=changed";
		}
	}

	@PostMapping("/register")
	public String doPostRegister(@ModelAttribute("userRequest") Users userRequest, HttpSession session) {
		try {
			Users userResponse = userService.save(userRequest);
			if (userResponse != null) {
				String username = userResponse.getUsername();
				String email = userResponse.getEmail();
				String activeToken = userResponse.getActive();
				String activeLink = SessionUtil.getSiteURL(request) + "/active-account?token=" + activeToken;
//				session.setAttribute(SessionConstant.CURRENT_USER, userResponse);
				sendEmail2(email, username, activeLink);
				return "redirect:/login";
			} else {
				return "redirect:/login";
			}
		} catch (Exception e) {
			return "redirect:/register";
		}
	}

	private void sendEmail(String email, String username, String resetPasswordLink)
			throws UnsupportedEncodingException, MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("contact@shop.com", "Shop Support");
		helper.setTo(email);

		String subject = "Here's the link to reset your password";

		String content = "<p>Hello, username: " + username + "</p>"
				+ "<p>You have requested to reset your password.</p>"
				+ "<p>Click the link below to change your password:</p>" + "<p><a href=\"" + resetPasswordLink
				+ "\">Change my Password</a></p>" + "<br>" + "<p>Ignore this email if you do remember your password,"
				+ " or you have not made the request.</p>";
		helper.setSubject(subject);
		helper.setText(content, true);
		mailSender.send(message);
	}
	
	private void sendEmail2(String email, String username, String activeLink)
			throws UnsupportedEncodingException, MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("contact@shop.com", "Shop Support");
		helper.setTo(email);

		String subject = "Here's the link to active your account";

		String content = "<p>Hello, username: " + username + "</p>"
				+ "<p>You have requested to create an account.</p>"
				+ "<p>Click the link below to active your account:</p>" + "<p><a href=\"" + activeLink
				+ "\">Active your account</a></p>" + "<br>" + "<p>Ignore this email if you have not made the request.</p>";
		helper.setSubject(subject);
		helper.setText(content, true);
		mailSender.send(message);
	}
	
}
