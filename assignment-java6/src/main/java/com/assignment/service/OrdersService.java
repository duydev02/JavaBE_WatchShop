package com.assignment.service;

import java.util.List;

import com.assignment.entity.Orders;

public interface OrdersService {

	Orders insert(Orders order);
	List<Orders> findByUserId(Long userId);
}
