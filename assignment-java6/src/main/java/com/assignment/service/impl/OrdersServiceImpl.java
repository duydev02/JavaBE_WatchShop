package com.assignment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.entity.Orders;
import com.assignment.repository.OrdersRepo;
import com.assignment.service.OrdersService;

@Service
public class OrdersServiceImpl implements OrdersService {

	@Autowired
	private OrdersRepo repo;
	
	@Override
	public Orders insert(Orders order) {
		return repo.saveAndFlush(order);
	}

	@Override
	public List<Orders> findByUserId(Long userId) {
		return repo.findByUserId(userId);
	}

}
