package com.assignment.service;

import com.assignment.entity.Roles;

public interface RolesService {
	
	Roles findByDescription(String description);
}
