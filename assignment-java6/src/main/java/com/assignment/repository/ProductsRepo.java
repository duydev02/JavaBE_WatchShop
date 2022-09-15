package com.assignment.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.assignment.entity.Products;

@Repository
public interface ProductsRepo extends JpaRepository<Products, Long> {
	
	List<Products> findByIsDeleted(Boolean isDeleted);
	List<Products> findByIsDeletedAndQuantityGreaterThan(Boolean isDeleted, Integer quantity);
	Page<Products> findByIsDeletedAndQuantityGreaterThan(Boolean isDeleted, Integer quantity, Pageable pageable);
	
	@Query(value = "SELECT * FROM products WHERE isDeleted = ? AND quantity > ? ORDER BY price DESC", nativeQuery = true)
	List<Products> findAllByPriceDESC(Boolean isDeleted, Integer quantity);
	
	@Query(value = 
			"SELECT TOP(5) products.id, products.name, products.typeId, products.brandId, products.quantity, products.price, products.unitId, products.imgUrl, CAST(products.description AS NVARCHAR(1000)) as description, products.slug, products.isDeleted "
			+ "FROM products "
			+ "LEFT JOIN order_details ON products.id = order_details.productId "
			+ "WHERE products.isDeleted = ? AND products.quantity > ? "
			+ "GROUP BY products.id, products.name, products.typeId, products.brandId, products.quantity, products.price, products.unitId, products.imgUrl, CAST(products.description AS NVARCHAR(1000)), products.slug, products.isDeleted "
			+ "ORDER BY SUM(order_details.quantity) DESC"
			, nativeQuery = true)
	List<Products> findAllByPopular(Boolean isDeleted, Integer quantity);
	
	Products findBySlug(String slug);
	
	@Query(value = 
			"SELECT products.* "
			+ "FROM products LEFT JOIN brand_types ON products.brandId = brand_types.id "
			+ "WHERE products.isDeleted = ? AND quantity > ? AND brand_types.slug = ?"
			, nativeQuery = true)
	List<Products> findAllByBrandType(Boolean isDeleted, Integer quantity, String brandType);
	
	@Query(value = 
			"SELECT products.* "
			+ "FROM products LEFT JOIN brand_types ON products.brandId = brand_types.id "
			+ "WHERE products.isDeleted = ? AND quantity > ? AND brand_types.slug = ? "
			+ "ORDER BY price DESC"
			, nativeQuery = true)
	List<Products> findAllByPriceDESCAndBrandType(Boolean isDeleted, Integer quantity, String brandType);
	
	@Query(value = 
			"SELECT top(5) products.id, products.name, products.typeId, products.brandId, products.quantity, products.price, products.unitId, products.imgUrl, CAST(products.description AS NVARCHAR(1000)) as description, products.slug, products.isDeleted "
			+ "FROM products "
			+ "LEFT JOIN order_details ON products.id = order_details.productId LEFT JOIN brand_types ON products.brandId = brand_types.id "
			+ "WHERE products.isDeleted = ? AND products.quantity > ? AND brand_types.slug = ? "
			+ "GROUP BY products.id, products.name, products.typeId, products.brandId, products.quantity, products.price, products.unitId, products.imgUrl, CAST(products.description AS NVARCHAR(1000)), products.slug, products.isDeleted "
			+ "ORDER BY SUM(order_details.quantity) DESC"
			, nativeQuery = true)
	List<Products> findAllByPopularAndBrandType(Boolean isDeleted, Integer quantity, String brandType);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE products SET quantity = ? WHERE id = ?", nativeQuery = true)
	void updateQuantity(Integer newQuantity, Long productId);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE products SET isDeleted = 1 WHERE id = ?", nativeQuery = true)
	void deleteLogical(Long id);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "INSERT INTO products"
			+ "([name], typeId, brandId, quantity, price, unitId, imgUrl, [description], slug) VALUES "
			+ "(?, ?, ?, ?, ?, ?, ?, ?, ?)"
			, nativeQuery = true)
	void save(String name, Long productType, Long brandType, Integer quantity, Double price, Long unitType,
			String imgUrl, String description, String slug);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE products SET "
			+ "[name] = ?, typeId = ?, brandId = ?, quantity = ?, price = ?, unitId = ?, imgUrl = ?, [description] = ?, slug = ? WHERE id = ?"
			, nativeQuery = true)
	void update(String name, Long productType, Long brandType, Integer quantity, Double price, Long unitType,
			String imgUrl, String description, String slug, Long id);
}
