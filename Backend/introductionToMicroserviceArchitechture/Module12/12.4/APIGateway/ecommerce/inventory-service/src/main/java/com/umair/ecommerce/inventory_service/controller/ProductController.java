package com.umair.ecommerce.inventory_service.controller;


import com.umair.ecommerce.inventory_service.dto.ProductDto;
import com.umair.ecommerce.inventory_service.entity.Product;
import com.umair.ecommerce.inventory_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;

    @GetMapping("/fetchOrders")
    public String fetchOrders() {
        ServiceInstance orders = discoveryClient.getInstances("order-service").getFirst();

       return restClient.get()
                .uri(orders.getUri() + "/api/v1/orders/hello")
                .retrieve()
                .body(String.class);


    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {

        List<ProductDto> inventories = productService.getAllInventory();
        return ResponseEntity.ok().body(inventories);

    }

    @GetMapping("/id")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        ProductDto product = productService.getInventoryById(id);
        return ResponseEntity.ok().body(product);
    }
}
