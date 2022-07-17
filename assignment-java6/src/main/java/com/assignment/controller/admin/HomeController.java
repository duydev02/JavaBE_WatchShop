package com.assignment.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.assignment.service.StatsService;

@Controller(value = "HomeControllerOfAdmin")
@RequestMapping("/admin")
public class HomeController {

	@Autowired
	private StatsService statsService;
	
	@GetMapping("")
	public String doGetIndex(Model model) {
		String[][] chartData = statsService.getTotalPriceLast6Month();
		model.addAttribute("chartData", chartData);
		return "admin/index";
	}
}
