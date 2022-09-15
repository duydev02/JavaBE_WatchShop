package com.assignment.repository;

import com.assignment.entity.ProductTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypesRepo extends JpaRepository<ProductTypes, Long> {

}
