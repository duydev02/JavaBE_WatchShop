package com.assignment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.entity.Roles;
import com.assignment.repository.RolesRepo;
import com.assignment.service.RolesService;

@Service
public class RolesServiceImpl implements RolesService {

	@Autowired
	private RolesRepo repo;
	
	@Override
	public Roles findByDescription(String description) {
		return repo.findByDescription(description);
	}
	
}
