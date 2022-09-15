package com.assignment.service.impl;

import com.assignment.entity.UnitTypes;
import com.assignment.repository.UnitTypesRepo;
import com.assignment.service.UnitTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitTypesServiceImpl implements UnitTypesService {

    @Autowired
    private UnitTypesRepo repo;

    @Override
    public List<UnitTypes> findAll() {
        return repo.findAll();
    }
}
