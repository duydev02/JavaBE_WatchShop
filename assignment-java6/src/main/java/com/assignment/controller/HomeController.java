package com.assignment.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.assignment.constant.SessionConstant;
import com.assignment.entity.BrandTypes;
import com.assignment.entity.Products;
import com.assignment.entity.Users;
import com.assignment.service.AuthenticationService;
import com.assignment.service.BrandTypesService;
import com.assignment.service.ProductsService;
import com.assignment.service.SessionService;

@Controller
public class HomeController {

	@Autowired
	SessionService sessionService;

	@Autowired
	private ProductsService productService;

	@Autowired
	private BrandTypesService brandTypeService;
	
	@Autowired
	private AuthenticationService authenticationService;

//	private static final int MAX_SIZE = 3;

	@GetMapping(value = { "/index", "/" })
	public String doGetIndex(Model model) {
		model.addAttribute("titleMain", "Time zone");
		return "user/index";
	}

	@GetMapping("/shop")
	public String doGetShop(@RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model,
			HttpSession session) {
		model.addAttribute("titleMain", "Shop");

		// Show brand type
		List<BrandTypes> brandTypes = brandTypeService.findAll();
		model.addAttribute("brandTypes", brandTypes);

		// Show products in 3 selection
		if (ObjectUtils.isEmpty(session.getAttribute("maxSizePage"))) {
			session.setAttribute("maxSizePage", 3);
		}
		Integer maxSizePage = sessionService.get("maxSizePage");
		List<Products> products = new ArrayList<>();
		try {
			Page<Products> pageProducts = productService.findAll(maxSizePage, page);
			products = pageProducts.getContent();
			model.addAttribute("totalPages", pageProducts.getTotalPages());
			model.addAttribute("currentPage", page);
		} catch (Exception e) {
			products = productService.findAll();
		}
		model.addAttribute("products", products);
		List<Products> products2 = productService.findAllByPriceDESC();
		model.addAttribute("products2", products2);
		List<Products> products3 = productService.findAllByPopular();
		model.addAttribute("products3", products3);

		return "user/shop";
	}

	@GetMapping("/shop/{brandType}")
	public String doGetShopByBrandTypeProduct(@PathVariable("brandType") String brandType, Model model) {
		model.addAttribute("titleMain", "Shop");

		model.addAttribute("brandTypeSelected", brandType);
		// Show brand type
		List<BrandTypes> brandTypes = brandTypeService.findAll();
		model.addAttribute("brandTypes", brandTypes);

		// Show products in 3 selection
		List<Products> products = productService.findAllByBrandType(brandType);
		model.addAttribute("products", products);
		List<Products> products2 = productService.findAllByPriceDESCAndBrandType(brandType);
		model.addAttribute("products2", products2);
		List<Products> products3 = productService.findAllByPopularAndBrandType(brandType);
		model.addAttribute("products3", products3);

		return "user/shop";
	}

	@GetMapping("/product-details/{slug}")
	public String doGetProductDetails(@PathVariable("slug") String slug, Model model) {
		model.addAttribute("titleMain", "Product Details");

		Products product = productService.findBySlug(slug);
		if (product == null) {
			return "user/index";
		}
		model.addAttribute("product", product);

		return "user/product-details";
	}

	@GetMapping("/about")
	public String doGetAbout(Model model) {
		model.addAttribute("titleMain", "About us");
		return "user/about";
	}

	@GetMapping("/blog")
	public String doGetBlog(Model model) {
		model.addAttribute("titleMain", "Blog");
		return "user/blog";
	}

	@GetMapping("/blog-details")
	public String doGetBlogDetails(Model model) {
		model.addAttribute("titleMain", "Blog Details");
		return "user/blog-details";
	}

	@GetMapping("/contact")
	public String doGetContact(Model model) {
		model.addAttribute("titleMain", "Contact");
		return "user/contact";
	}

	@GetMapping("/login")
	public String doGetLogin(Model model) {
		model.addAttribute("titleMain", "Login");

		model.addAttribute("userRequest", new Users());
		return "user/login";
	}

	@GetMapping("/register")
	public String doGetRegister(Model model) {
		model.addAttribute("titleMain", "Register");

		model.addAttribute("userRequest", new Users());
		return "user/register";
	}

	@PostMapping("/login")
	public String doPostLogin(@ModelAttribute("userRequest") Users userRequest, HttpSession session) {
		try {
			Users userResponse = authenticationService.doLogin(userRequest.getUsername(), userRequest.getHashPassword(), session);
			if (userResponse == null) {
				return "redirect:/login";
			}
			session.setAttribute(SessionConstant.CURRENT_USER, userResponse);
			return "redirect:/index";
		} catch (Exception ex) {
			ex.printStackTrace();
			return "redirect:/login";
		}
	}
}
