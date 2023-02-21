package com.assignment.repository;

import com.assignment.entity.Products;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductsRepoTest {

    @Autowired
    private ProductsRepo productsRepo;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        Products product = Products.builder()
                .id(1L)
                .name("Samsung J7 Prime")
                .price(3900000.0)
                .quantity(10)
                .slug("j7prime")
                .build();
        entityManager.persist(product);
    }

    @Test
    public void whenFindBySlug_thenReturnProduct() {
        Products product = productsRepo.findBySlug("j7prime");
        assertEquals(product.getName(), "Samsung J7 Prime");
    }
}