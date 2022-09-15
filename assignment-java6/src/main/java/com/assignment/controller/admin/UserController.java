package com.assignment.controller.admin;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.assignment.entity.Users;
import com.assignment.service.UsersService;

@Controller
@RequestMapping("/admin/user")
public class UserController {

	@Autowired
	private UsersService userService;

	@GetMapping("")
	public String doGetIndex(Model model) {
		List<Users> users = userService.findAll();
		model.addAttribute("users", users);
		model.addAttribute("userRequest", new Users());
		return "admin/user";
	}

	// /admin/user/delete?username={...}
	@GetMapping("/delete")
	public String doGetDelete(@RequestParam("username") String username, RedirectAttributes redirectAttributes) {
		try {
			userService.deleteLogical(username);
			redirectAttributes.addFlashAttribute("succeedMessage", "User " + username + " was deleted");
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMessage", "Cannot delete user " + username);
		}
		return "redirect:/admin/user";
	}

	@GetMapping("/edit")
	public String doGetEditUser(@RequestParam("username") String username, Model model) {
		Users userRequest = userService.findByUsername(username);
		model.addAttribute("userRequest", userRequest);
		return "admin/user::#form";
	}

	@PostMapping("/create")
	public String doPostCreateUser(@Valid @ModelAttribute("userRequest") Users userRequest, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, @RequestParam("attach") MultipartFile attach) {
		if (bindingResult.hasErrors()) {
			String error = "";
			outerloop: for (Object object : bindingResult.getAllErrors()) {
				if (object instanceof ObjectError) {
					ObjectError objectError = (ObjectError) object;
					System.out.println(objectError.getCode());
					switch (objectError.getCode()) {
					case "Size":
						error = "Fullname";
						break outerloop;
					case "Email":
						error = "Email";
						break outerloop;
					default:
						error = "...";
						break outerloop;
					}
				}
			}
			redirectAttributes.addFlashAttribute("errorMessage", "User's " + error + " is not valid");
		} else {
			try {
				if (!attach.isEmpty()) {
					System.out.println("alo");
					Path path = Paths.get("images/user-avatar/");
					
					if(!Files.exists(path)) {
						Files.createDirectories(path);
					}
					
					InputStream inputStream = attach.getInputStream();
					Files.copy(inputStream, path.resolve(attach.getOriginalFilename()),
							StandardCopyOption.REPLACE_EXISTING);
					userRequest.setImgUrl(attach.getOriginalFilename());
				}
				userService.save(userRequest);
				redirectAttributes.addFlashAttribute("succeedMessage",
						"User " + userRequest.getUsername() + " has been created successfully");
			} catch (Exception e) {
				e.printStackTrace();
				redirectAttributes.addFlashAttribute("succeedMessage",
						"Cannot create user: " + userRequest.getUsername());
			}
		}
		return "redirect:/admin/user";
	}

	@PostMapping("/edit")
	public String doPostEditUser(@Valid @ModelAttribute("userRequest") Users userRequest, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, @RequestParam("attach") MultipartFile attach) {
		if (bindingResult.hasErrors()) {
			String error = "";
			outerloop: for (Object object : bindingResult.getAllErrors()) {
				if (object instanceof ObjectError) {
					ObjectError objectError = (ObjectError) object;
					System.out.println(objectError.getCode());
					switch (objectError.getCode()) {
					case "Size":
						error = "Fullname";
						break outerloop;
					case "Email":
						error = "Email";
						break outerloop;
					default:
						error = "...";
						break outerloop;
					}
				}
			}
			redirectAttributes.addFlashAttribute("errorMessage", "User's " + error + " is not valid");
		} else {
			try {
				if (!attach.isEmpty()) {
					System.out.println("alo");
					Path path = Paths.get("images/user-avatar/");
					
					if(!Files.exists(path)) {
						Files.createDirectories(path);
					}
					
					InputStream inputStream = attach.getInputStream();
					Files.copy(inputStream, path.resolve(attach.getOriginalFilename()),
							StandardCopyOption.REPLACE_EXISTING);
					userRequest.setImgUrl(attach.getOriginalFilename());
				} else {
					Users checkUser = userService.findByUsername(userRequest.getUsername());
					userRequest.setImgUrl(checkUser.getImgUrl());
				}
				userService.update(userRequest);
				redirectAttributes.addFlashAttribute("succeedMessage",
						"User " + userRequest.getUsername() + " has been edited successfully");
			} catch (Exception e) {
				e.printStackTrace();
				redirectAttributes.addFlashAttribute("errorMessage", "Cannot edit user: " + userRequest.getUsername());
			}
		}
		return "redirect:/admin/user";
	}

}
