package com.assignment.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.assignment.entity.Products;

public interface ProductsService {
	
	List<Products> findByIsDeleted();
	List<Products> findAll();
	Page<Products> findAll(int pageSize, int pageNumber) throws Exception;
	List<Products> findAllByPriceDESC();
	List<Products> findAllByPopular();
	
	List<Products> findAllByBrandType(String brandType);
	List<Products> findAllByPriceDESCAndBrandType(String brandType);
	List<Products> findAllByPopularAndBrandType(String brandType);
	
	Products findBySlug(String slug);
	Products findById(Long id);
	
	void updateQuantity(Integer newQuantity, Long productId);
	void deleteLogical(Long id);
	void save(Products productRequest, Long productType, Long brandType, Long unitType);
	void update(Products productRequest, Long productType, Long brandType, Long unitType);
}
