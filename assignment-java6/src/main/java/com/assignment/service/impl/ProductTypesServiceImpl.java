package com.assignment.service.impl;

import com.assignment.entity.ProductTypes;
import com.assignment.repository.ProductTypesRepo;
import com.assignment.service.ProductTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductTypesServiceImpl implements ProductTypesService {

    @Autowired
    private ProductTypesRepo repo;

    @Override
    public List<ProductTypes> findAll() {
        return repo.findAll();
    }
}
