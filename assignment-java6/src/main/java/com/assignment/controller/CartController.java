package com.assignment.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.assignment.dto.CartDto;
import com.assignment.service.CartService;
import com.assignment.util.SessionUtil;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	CartService cartService;

	@GetMapping("")
	public String doGetCart(Model model) {
		model.addAttribute("titleMain", "Cart");
		return "user/cart";
	}

	// Su dung ky thuat reload fragment in thymleaf:
	@GetMapping("/update")
	public String doGetUpdate(@RequestParam("productId") Long productId, @RequestParam("quantity") Integer quantity,
			@RequestParam("isReplace") Boolean isReplace, HttpSession session) {
		CartDto currentCart = SessionUtil.getCurrentCart(session);
		cartService.updateCart(currentCart, productId, quantity, isReplace);
		return "user/cart::#viewCartFragment";
	}
}
