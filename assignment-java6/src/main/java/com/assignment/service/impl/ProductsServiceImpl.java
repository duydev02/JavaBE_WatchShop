package com.assignment.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.assignment.entity.Products;
import com.assignment.repository.ProductsRepo;
import com.assignment.service.ProductsService;

@Service
public class ProductsServiceImpl implements ProductsService {

	@Autowired
	private ProductsRepo repo;

	@Override
	public List<Products> findByIsDeleted() {
		return repo.findByIsDeleted(Boolean.FALSE);
	}

	// Tat ca san pham
	@Override
	public List<Products> findAll() {
		return repo.findByIsDeletedAndQuantityGreaterThan(Boolean.FALSE, 0);
	}

	@Override
	public List<Products> findAllByPriceDESC() {
		return repo.findAllByPriceDESC(Boolean.FALSE, 0);
	}

	@Override
	public List<Products> findAllByPopular() {
		return repo.findAllByPopular(Boolean.FALSE, 0);
	}

	// San pham theo loai thuong hieu
	@Override
	public List<Products> findAllByBrandType(String brandType) {
		return repo.findAllByBrandType(Boolean.FALSE, 0, brandType);
	}

	@Override
	public List<Products> findAllByPriceDESCAndBrandType(String brandType) {
		return repo.findAllByPriceDESCAndBrandType(Boolean.FALSE, 0, brandType);
	}

	@Override
	public List<Products> findAllByPopularAndBrandType(String brandType) {
		return repo.findAllByPopularAndBrandType(Boolean.FALSE, 0, brandType);
	}

	//
	@Override
	public Products findBySlug(String slug) {
		return repo.findBySlug(slug);
	}

	@Override
	public Products findById(Long id) {
		Optional<Products> optional = repo.findById(id);
		return optional.isPresent() ? optional.get() : null;
	}

	@Override
	public Page<Products> findAll(int pageSize, int pageNumber) throws Exception {
		if (pageNumber >= 1) {
			return repo.findByIsDeletedAndQuantityGreaterThan(Boolean.FALSE, 0,
					PageRequest.of(pageNumber - 1, pageSize));
		} else {
			throw new Exception("Page number must be greater than 0");
		}
	}

	@Override
	public void updateQuantity(Integer newQuantity, Long productId) {
		repo.updateQuantity(newQuantity, productId);
	}

	@Override
	@Transactional(rollbackOn = { Exception.class, Throwable.class })
	public void deleteLogical(Long id) {
		repo.deleteLogical(id);
	}

	@Override
	@Transactional(rollbackOn = { Exception.class, Throwable.class })
	public void save(Products productRequest, Long productType, Long brandType, Long unitType) {
		String name = productRequest.getName();
		// productType
		// brandType
		Integer quantity = productRequest.getQuantity();
		Double price = productRequest.getPrice();
		// unitType
		String imgUrl = productRequest.getImgUrl();
		String description = productRequest.getDescription();
		String slug = productRequest.getSlug();
		repo.save(name, productType, brandType, quantity, price, unitType, imgUrl, description, slug);
	}

	@Override
	@Transactional(rollbackOn = { Exception.class, Throwable.class })
	public void update(Products productRequest, Long productType, Long brandType, Long unitType) {
		String name = productRequest.getName();
		// productType
		// brandType
		Integer quantity = productRequest.getQuantity();
		Double price = productRequest.getPrice();
		// unitType
		String imgUrl = productRequest.getImgUrl();
		String description = productRequest.getDescription();
		String slug = productRequest.getSlug();
		Long id = productRequest.getId();
		repo.update(name, productType, brandType, quantity, price, unitType, imgUrl, description, slug, id);
	}

}
