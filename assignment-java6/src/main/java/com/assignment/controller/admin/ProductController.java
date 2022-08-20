package com.assignment.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.assignment.entity.BrandTypes;
import com.assignment.entity.Products;
import com.assignment.service.BrandTypesService;
import com.assignment.service.ProductsService;

@Controller
@RequestMapping("/admin/product")
public class ProductController {

	@Autowired
	private ProductsService productService;

	@Autowired
	private BrandTypesService brandTypeService;

	@GetMapping("")
	public String doGetIndex(Model model) {
		// Show brand type
		List<BrandTypes> brandTypes = brandTypeService.findAll();
		model.addAttribute("brandTypes", brandTypes);
		
		List<Products> products = productService.findByIsDeleted();
		model.addAttribute("products", products);
		return "admin/product";
	}

	// /admin/product/delete?id={...}
	@GetMapping("/delete")
	public String doGetDelete(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
		try {
			productService.deleteLogical(id);
			redirectAttributes.addFlashAttribute("succeedMessage", "Product " + id + " has been deleted");
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMessage", "Cannot create product " + id);
		}
		return "redirect:/admin/product";
	}

}
