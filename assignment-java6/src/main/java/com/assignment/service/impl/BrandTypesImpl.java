package com.assignment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.entity.BrandTypes;
import com.assignment.repository.BrandTypesRepo;
import com.assignment.service.BrandTypesService;

@Service
public class BrandTypesImpl implements BrandTypesService {

	@Autowired
	private BrandTypesRepo repo;
	
	@Override
	public List<BrandTypes> findAll() {
		return repo.findAll();
	}

}
