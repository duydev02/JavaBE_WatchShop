package com.assignment.controller;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.assignment.dto.ChangePassword;
import com.assignment.entity.OrderDetails;
import com.assignment.entity.Orders;
import com.assignment.entity.Users;
import com.assignment.service.OrderDetailsService;
import com.assignment.service.OrdersService;
import com.assignment.service.SessionService;
import com.assignment.service.UsersService;

@Controller
public class UserProfileController {

	@Autowired
	private UsersService userService;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private OrdersService orderService;
	
	@Autowired
	private OrderDetailsService orderDetailsService;

	@GetMapping("/profile/{username}")
	public String doGetProfile(@PathVariable("username") String username, Model model) {
		model.addAttribute("titleMain", "My profile");

		Users user = sessionService.get("currentUser");
		String currentUsername = user.getUsername();

		List<Orders> orders = orderService.findByUserId(user.getId());
		model.addAttribute("orders", orders);
		model.addAttribute("userRequest", user);
		model.addAttribute("changePassword", new ChangePassword());
		if (!username.equals(currentUsername)) {
			return "redirect:/index";
		}
		return "user/profile";
	}
	
	@GetMapping("/profile/order/orderdetails")
	public String doGetOrderDetails(@RequestParam("id") Long id, Model model) {
		List<OrderDetails> orderDetails = orderDetailsService.findByOrderId(id);
		Orders order = orderService.findById(id);
		model.addAttribute("order", order);
		model.addAttribute("orderDetails", orderDetails);
		return "admin/order::#table-order-details";
	}

	@PostMapping("/profile/change-password/{username}")
	public String doPostProfile(@ModelAttribute("changePassword") ChangePassword changePassword,
			@PathVariable("username") String username, Model model, RedirectAttributes redirectAttributes) {
		Users user = sessionService.get("currentUser");
		String currentUsername = user.getUsername();
		if (!username.equals(currentUsername)) {
			return "redirect:/index";
		}
		try {
			userService.changePassword(changePassword, username);
			redirectAttributes.addFlashAttribute("succeedMessage", "Change password successfully!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("succeedMessage", "Change password error!");
		}
		return "redirect:/profile/" + username;
	}

	@PostMapping("/profile/change")
	public String doPostChange(@ModelAttribute("userRequest") Users user, RedirectAttributes redirectAttributes,
			@RequestParam("attach") MultipartFile attach) {
		Users user2 = sessionService.get("currentUser");
		String fullname = user.getFullname();
		try {
			if (!attach.isEmpty()) {
				System.out.println("alo");
				Path path = Paths.get("images/user-avatar/");

				if (!Files.exists(path)) {
					Files.createDirectories(path);
				}

				InputStream inputStream = attach.getInputStream();
				Files.copy(inputStream, path.resolve(attach.getOriginalFilename()),
						StandardCopyOption.REPLACE_EXISTING);
			}
			String newImage = attach.getOriginalFilename();
			userService.change(user2, fullname, newImage);
			redirectAttributes.addFlashAttribute("succeedMessage", "Change profile successfully!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("succeedMessage", "Change profile error!");
		}
		return "redirect:/profile/" + user2.getUsername();
	}

}
