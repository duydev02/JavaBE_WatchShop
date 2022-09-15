package com.assignment.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.assignment.entity.OrderDetails;
import com.assignment.entity.Orders;
import com.assignment.service.OrderDetailsService;
import com.assignment.service.OrdersService;

@Controller
@RequestMapping("/admin/order")
public class OrderController {

	@Autowired
	private OrdersService orderService;
	
	@Autowired
	private OrderDetailsService orderDetailsService;

	@GetMapping("")
	public String doGetIndex(Model model) {
		List<Orders> orders = orderService.findAll();
		model.addAttribute("orders", orders);
		return "admin/order";
	}
	
	@GetMapping("/orderdetails")
	public String doGetOrderDetails(@RequestParam("id") Long id, @RequestParam("username") String username,
			@RequestParam("totalprice") Double totalprice ,Model model) {
		List<OrderDetails> orderDetails = orderDetailsService.findByOrderId(id);
		model.addAttribute("orderDetails", orderDetails);
		model.addAttribute("username", username);
		model.addAttribute("totalprice", totalprice);
		return "admin/order::#table-order-details";
	}
}
