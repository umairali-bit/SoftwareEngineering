package com.example.sortingAndPagination.sortingAndPagination.controllers;

import com.example.sortingAndPagination.sortingAndPagination.entities.ProductEntity;
import com.example.sortingAndPagination.sortingAndPagination.repositories.ProductRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/products")
public class ProductController {

    private final ProductRepository productRepository;


    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<ProductEntity> getAllProductsByTitle() {
        return productRepository.findByTitleOrderByPrice("coca cola");
    }

    @GetMapping(path = "/")
    public List<ProductEntity> getAllProducts() {
        return productRepository.findByOrderByPrice();

    }


}
