package com.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.entity.BrandTypes;

@Repository
public interface BrandTypesRepo extends JpaRepository<BrandTypes, Long> {
	
}
