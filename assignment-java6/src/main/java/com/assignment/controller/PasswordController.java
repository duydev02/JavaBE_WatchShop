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
			sendEmailResetPassword(email, username, resetPasswordLink);
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
				sendEmailRegister(email, username, activeLink);
				return "redirect:/login";
			} else {
				return "redirect:/login";
			}
		} catch (Exception e) {
			return "redirect:/register";
		}
	}

	private void sendEmailResetPassword(String email, String username, String resetPasswordLink)
			throws UnsupportedEncodingException, MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("contact@shop.com", "Shop Support");
		helper.setTo(email);

		String subject = "Here's the link to reset your password";
		String subTitle = "Reset your password";
		String subContent = " You have requested a password reset. Please click on the below link to reset your password: ";
		String subButton = "Reset Now";

		String content = contentMail(username, resetPasswordLink, subTitle, subContent, subButton);
		helper.setSubject(subject);
		helper.setText(content, true);
		mailSender.send(message);
	}
	
	private void sendEmailRegister(String email, String username, String activeLink)
			throws UnsupportedEncodingException, MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("contact@shop.com", "Shop Support");
		helper.setTo(email);

		String subject = "Here's the link to active your account";
		String subTitle = "Confirm your email";
		String subContent = " Thank you for registering. Please click on the below link to activate your account: ";
		String subButton = "Active Now";

		String content = contentMail(username, activeLink, subTitle, subContent, subButton);
		helper.setSubject(subject);
		helper.setText(content, true);
		mailSender.send(message);
	}
	
	private String contentMail(String username, String link, String title, String content, String subButton) {
		return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">" + title + "</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + username + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> " + content + "</p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">" + subButton + "</a> </p></blockquote>\n<p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
	}
}
