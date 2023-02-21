package com.assignment.controller;

import com.assignment.config.WebSecurityConfig;
import com.assignment.entity.Products;
import com.assignment.service.ProductsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = HomeController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductsService productsService;

    private Products product;

    @BeforeEach
    void setUp() {
        product = Products.builder()
                .id(1L)
                .name("Samsung J7 Prime")
                .price(3900000.0)
                .quantity(10)
                .slug("j7prime")
                .build();
    }

    @Test
    void doGetProductDetailsTest() throws Exception {
        Mockito.when(productsService.findBySlug("j7prime"))
                .thenReturn(product);

        mockMvc.perform(get("/product-details/j7prime")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name")
                        .value(product.getName()));
    }
}