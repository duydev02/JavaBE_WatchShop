package com.assignment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.assignment.dto.CartDetailDto;
import com.assignment.entity.OrderDetails;

@Repository
public interface OrderDetailsRepo extends JpaRepository<OrderDetails, Long> {

	@Modifying(clearAutomatically = true) // Cap nhat o cac bang lien quan khi co thay doi
	@Query(value = "INSERT INTO order_details(orderId, productId, price, quantity)"
			+ " VALUES (:#{#dto.orderId}, :#{#dto.productId}, :#{#dto.price}, :#{#dto.quantity})", nativeQuery = true)
	void insert(@Param("dto") CartDetailDto cartDetailDto);

	List<OrderDetails> findByOrderId(Long id);
}
