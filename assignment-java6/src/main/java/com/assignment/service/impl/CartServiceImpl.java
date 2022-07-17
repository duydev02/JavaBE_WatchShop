package com.assignment.service.impl;

import java.util.HashMap;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.dto.CartDetailDto;
import com.assignment.dto.CartDto;
import com.assignment.entity.Orders;
import com.assignment.entity.Products;
import com.assignment.entity.Users;
import com.assignment.service.CartService;
import com.assignment.service.OrderDetailsService;
import com.assignment.service.OrdersService;
import com.assignment.service.ProductsService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private ProductsService productService;

	@Autowired
	private OrdersService orderService;

	@Autowired
	private OrderDetailsService orderDetailService;

	@Override
	public CartDto updateCart(CartDto cart, Long productId, Integer quantity, boolean isReplace) {
		Products product = productService.findById(productId);

		HashMap<Long, CartDetailDto> details = cart.getDetails();

		if (!details.containsKey(productId)) {
			CartDetailDto newDetail = createNewCartDetail(product, quantity);
			details.put(productId, newDetail);
		} else if (quantity > 0) {
			if (isReplace) {
				details.get(productId).setQuantity(quantity);
			} else {
				Integer oldQuantity = details.get(productId).getQuantity();
				Integer newQuantity = oldQuantity + quantity;
				details.get(productId).setQuantity(newQuantity);
			}
		} else {
			details.remove(productId);
		}
		cart.setTotalQuantity(getTotalQuantity(cart));
		cart.setTotalPrice(getTotalPrice(cart));
		return cart;
	}

	@Override
	public Integer getTotalQuantity(CartDto cart) {
		Integer totalQuantity = 0;
		HashMap<Long, CartDetailDto> details = cart.getDetails();
		for (CartDetailDto cartDetail : details.values()) {
			totalQuantity += cartDetail.getQuantity();
		}
		return totalQuantity;
	}

	@Override
	public Double getTotalPrice(CartDto cart) {
		Double totalPrice = 0D;
		HashMap<Long, CartDetailDto> details = cart.getDetails();
		for (CartDetailDto cartDetail : details.values()) {
			totalPrice += cartDetail.getPrice() * cartDetail.getQuantity();
		}
		return totalPrice;
	}

	@Transactional(rollbackOn = { Exception.class, Throwable.class })
	@Override
	public void insert(CartDto cart, Users user, String address, String phone) throws Exception {
		
		if (StringUtils.isAnyBlank(address, phone)) {
			throw new NullPointerException("Address or phone number must be not null or empty or whitespace");
		}

		// insert vao ORDER
		Orders order = new Orders();
		order.setUser(user);
		order.setAddress(address);
		order.setPhone(phone);

		Orders orderResponse = orderService.insert(order);

		// duyet hashmap de insert lan luot vao ORDER_DETAILS
		// trong luc duyet hashmap qua tung SP -> di update quantity cho tung SP do
		double totalPrice = 0;
		long orderId = orderResponse.getId();
		for (CartDetailDto cartDetail : cart.getDetails().values()) {
			Products product = productService.findById(cartDetail.getProductId());
			if (product.getQuantity() >= cartDetail.getQuantity()) {
				cartDetail.setOrderId(orderId);
				orderDetailService.insert(cartDetail);

				Integer newQuantity = product.getQuantity() - cartDetail.getQuantity();
				productService.updateQuantity(newQuantity, cartDetail.getProductId());
				totalPrice += cartDetail.getPrice() * cartDetail.getQuantity();
			} else {
				throw new IndexOutOfBoundsException("Order quantity must be less than the number of products");
			}
		}
		order.setTotalPrice(totalPrice);
		orderService.insert(order);
	}

	private CartDetailDto createNewCartDetail(Products product, Integer quantity) {
		CartDetailDto cartDetail = new CartDetailDto();
		cartDetail.setProductId(product.getId());
		cartDetail.setPrice(product.getPrice());
		cartDetail.setQuantity(quantity);
		cartDetail.setSlug(product.getSlug());
		cartDetail.setName(product.getName());
		cartDetail.setImgUrl(product.getImgUrl());
		return cartDetail;
	}

}
