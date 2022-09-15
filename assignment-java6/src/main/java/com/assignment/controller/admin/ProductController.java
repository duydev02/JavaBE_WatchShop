package com.assignment.controller.admin;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.assignment.entity.BrandTypes;
import com.assignment.entity.ProductTypes;
import com.assignment.entity.Products;
import com.assignment.entity.UnitTypes;
import com.assignment.service.BrandTypesService;
import com.assignment.service.ProductTypesService;
import com.assignment.service.ProductsService;
import com.assignment.service.UnitTypesService;

@Controller
@RequestMapping("/admin/product")
public class ProductController {

	@Autowired
	private ProductsService productService;

	@Autowired
	private ProductTypesService productTypesService;

	@Autowired
	private BrandTypesService brandTypeService;

	@Autowired
	private UnitTypesService unitTypesService;

	@GetMapping("")
	public String doGetIndex(Model model) {
		// Show product type
		List<ProductTypes> productTypes = productTypesService.findAll();
		model.addAttribute("productTypes", productTypes);
		// Show brand type
		List<BrandTypes> brandTypes = brandTypeService.findAll();
		model.addAttribute("brandTypes", brandTypes);
		// Show unit type
		List<UnitTypes> unitTypes = unitTypesService.findAll();
		model.addAttribute("unitTypes", unitTypes);
		
		List<Products> products = productService.findByIsDeleted();
		model.addAttribute("products", products);
		model.addAttribute("productRequest", new Products());
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
	
	@GetMapping("/edit")
	public String doGetEditProduct(@RequestParam("id") Long id, Model model) {
		Products productRequest = productService.findById(id);
		model.addAttribute("productRequest", productRequest);
		// Show product type
		List<ProductTypes> productTypes = productTypesService.findAll();
		model.addAttribute("productTypes", productTypes);
		// Show brand type
		List<BrandTypes> brandTypes = brandTypeService.findAll();
		model.addAttribute("brandTypes", brandTypes);
		// Show unit type
		List<UnitTypes> unitTypes = unitTypesService.findAll();
		model.addAttribute("unitTypes", unitTypes);
		Long productTypeSelected = productRequest.getProductType().getId();
		Long brandTypeSelected = productRequest.getBrandType().getId();
		Long unitTypeSelected = productRequest.getUnitType().getId();
		model.addAttribute("productTypeSelected", productTypeSelected);
		model.addAttribute("brandTypeSelected", brandTypeSelected);
		model.addAttribute("unitTypeSelected", unitTypeSelected);
		return "admin/product::#form";
	}
	
	@PostMapping("/create")
	public String doPostCreateProduct(@Valid @ModelAttribute("productRequest") Products productRequest, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, @RequestParam("productTypes") Long productType,
			@RequestParam("brandTypes") Long brandType, @RequestParam("unitTypes") Long unitType,
			@RequestParam("attach") MultipartFile attach) {
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("errorMessage", "Product is not valid");
		} else {
			try {
				if (!attach.isEmpty()) {
					URL url = this.getClass().getClassLoader().getResource("static/user/assets/img/gallery");
					File file = null;
				    try {
				        file = new File(url.toURI());
				    } catch (URISyntaxException e) {
				        file = new File(url.getPath());
				    }
				    String s = System.currentTimeMillis() + attach.getOriginalFilename();
					String name = Integer.toHexString(s.hashCode()) + s.substring(s.lastIndexOf("."));
					File savedFile = new File(file, name);
					attach.transferTo(savedFile);
					System.out.println(savedFile);
					System.out.println(attach);
					productRequest.setImgUrl(name);
				}
				productService.save(productRequest, productType, brandType, unitType);
				redirectAttributes.addFlashAttribute("succeedMessage",
						"Product " + productRequest.getName() + " has been created successfully");
			} catch (Exception e) {
				e.printStackTrace();
				redirectAttributes.addFlashAttribute("succeedMessage",
						"Cannot create product: " + productRequest.getName());
			}
		}
		return "redirect:/admin/product";
	}
	
	@PostMapping("/edit/{id}")
	public String doPostEditProduct(@Valid @ModelAttribute("productRequest") Products productRequest, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, @RequestParam("productTypes") Long productType,
			@RequestParam("brandTypes") Long brandType, @RequestParam("unitTypes") Long unitType,
			@RequestParam("attach") MultipartFile attach, @PathVariable("id") Long id) {
		Products checkProduct = productService.findById(id);
		if (checkProduct != null) {
			if (bindingResult.hasErrors()) {
				redirectAttributes.addFlashAttribute("errorMessage", "Product is not valid");
			} else {
				try {
					if (!attach.isEmpty()) {
						URL url = this.getClass().getClassLoader().getResource("static/user/assets/img/gallery");
						File file = null;
						try {
							file = new File(url.toURI());
						} catch (URISyntaxException e) {
							file = new File(url.getPath());
						}
						String s = System.currentTimeMillis() + attach.getOriginalFilename();
						String name = Integer.toHexString(s.hashCode()) + s.substring(s.lastIndexOf("."));
						File savedFile = new File(file, name);
						attach.transferTo(savedFile);
						System.out.println(savedFile);
						productRequest.setImgUrl(name);
					} else {
						productRequest.setImgUrl(checkProduct.getImgUrl());
					}
					productService.update(productRequest, productType, brandType, unitType);
					redirectAttributes.addFlashAttribute("succeedMessage",
							"Product " + productRequest.getName() + " has been edited successfully");
				} catch (Exception e) {
					e.printStackTrace();
					redirectAttributes.addFlashAttribute("succeedMessage",
							"Cannot edit product: " + productRequest.getName());
				}
			}
		} else {
			redirectAttributes.addFlashAttribute("errorMessage",
					"Cannot found product has id: " + id);
		}
		return "redirect:/admin/product";
	}

}
