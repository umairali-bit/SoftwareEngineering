package com.example.sortingAndPagination.sortingAndPagination.controllers;

import com.example.sortingAndPagination.sortingAndPagination.entities.ProductEntity;
import com.example.sortingAndPagination.sortingAndPagination.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/products")
public class ProductController {

    private final int PAGE_SIZE = 5;

    private final ProductRepository productRepository;


    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

//    @GetMapping
//    public List<ProductEntity> getAllProductsByTitle() {
//        return productRepository.findByTitleOrderByPrice("coca cola");
//    }

    @GetMapping(path = "/")
    public List<ProductEntity> getAllProducts() {
        return productRepository.findByOrderByPrice();

    }

    @GetMapping(path = "/sort")
    public List<ProductEntity> getSortedProducts(@RequestParam(defaultValue = "id") String sortBy) {
        return productRepository.findBy(Sort.by(sortBy));
        //desc, sort by price property if they are the same
        //return productRepository.findBy(Sort.by(Direction.DESC, sortBy, "price", "quantity));

        //return productRepository.findBy(Sort.by(
        // Sort.Order.desc(sortBy))),
        // Sort.Order.asc("price")



    }

    @GetMapping(path = "/pagination")
    public List<ProductEntity> getProducts(@RequestParam(defaultValue = "id") String sortBy,
                                           @RequestParam(defaultValue = "0") Integer pageNumber) {

        Pageable pageable = PageRequest.of(
                pageNumber,
                PAGE_SIZE,
                Sort.by(sortBy, "price"));
        return productRepository.findAll(pageable).getContent();
    }

    @GetMapping(path = "/pagination_title")
    public List<ProductEntity> getProductsByTitle(
            @RequestParam(defaultValue = "") String title,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "0") Integer pageNumber) {

        return productRepository.findByTitleContainingIgnoreCase(
                title,
                PageRequest.of(pageNumber, PAGE_SIZE, Sort.by(sortBy))
        );
    }



}
