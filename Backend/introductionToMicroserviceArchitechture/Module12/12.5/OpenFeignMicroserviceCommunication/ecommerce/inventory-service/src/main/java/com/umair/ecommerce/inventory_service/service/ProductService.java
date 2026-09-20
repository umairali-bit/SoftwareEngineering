package com.umair.ecommerce.inventory_service.service;


import com.umair.ecommerce.inventory_service.dto.ProductDto;
import com.umair.ecommerce.inventory_service.entity.Product;
import com.umair.ecommerce.inventory_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;


    public List<ProductDto> getAllInventory() {
        log.info("Getting all inventory");
        List<Product> inventories = productRepository.findAll();
        return inventories.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .toList();

    }

    public ProductDto getInventoryById(Long id) {
        log.info("Getting inventory with id {}", id);
        Optional<Product> inventory = productRepository.findById(id);
        return inventory.map(product -> modelMapper.map(product, ProductDto.class))
                .orElseThrow(() -> new RuntimeException("Product with id " + id + " not found"));

    }
}
