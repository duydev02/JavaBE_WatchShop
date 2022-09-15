package com.assignment.service;

import java.util.List;

import com.assignment.dto.CartDetailDto;
import com.assignment.entity.OrderDetails;

public interface OrderDetailsService {

	void insert(CartDetailDto cartDetailDto);

	List<OrderDetails> findByOrderId(Long id);
}
