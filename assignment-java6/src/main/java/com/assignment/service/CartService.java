package com.assignment.service;

import com.assignment.dto.CartDto;
import com.assignment.entity.Users;

public interface CartService {

	CartDto updateCart(CartDto cart, Long productId, Integer quantity, boolean isReplace);
	Integer getTotalQuantity(CartDto cart);
	Double getTotalPrice(CartDto cart);
	void insert(CartDto cart, Users user, String address, String phone) throws Exception;
}
