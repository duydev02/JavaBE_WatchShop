package com.assignment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.dto.CartDetailDto;
import com.assignment.repository.OrderDetailsRepo;
import com.assignment.service.OrderDetailsService;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

	@Autowired
	private OrderDetailsRepo repo;
	
	@Override
	public void insert(CartDetailDto cartDetailDto) {
		repo.insert(cartDetailDto);
	}

}
