package com.assignment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.entity.Orders;

@Repository
public interface OrdersRepo extends JpaRepository<Orders, Long> {

	List<Orders> findByUserId(Long userId);
}
