package com.umair.ecommerce.inventory_service.controller;


import com.umair.ecommerce.inventory_service.client.OrderFeignClient;
import com.umair.ecommerce.inventory_service.dto.OrderRequestDto;
import com.umair.ecommerce.inventory_service.dto.ProductDto;
import com.umair.ecommerce.inventory_service.entity.Product;
import com.umair.ecommerce.inventory_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    private final OrderFeignClient orderFeignClient;

    @GetMapping("/fetchOrders")
    public String fetchOrders() {
//        ServiceInstance orders = discoveryClient.getInstances("order-service").getFirst();

//       return restClient.get()
//                .uri(orders.getUri() + "/orders/core/hello")
//                .retrieve()
//                .body(String.class);

        return orderFeignClient.hello();


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

    @PutMapping("/reduced-stock")
    public ResponseEntity<Double> reducedStock(@RequestBody OrderRequestDto orderRequestDto) {

        Double totalPrice = productService.reduceStocks(orderRequestDto);
        return  ResponseEntity.ok().body(totalPrice);

    }
}
