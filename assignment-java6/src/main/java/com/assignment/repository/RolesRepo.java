package com.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.entity.Roles;

@Repository
public interface RolesRepo extends JpaRepository<Roles, Long> {

	Roles findByDescription(String description);
}
