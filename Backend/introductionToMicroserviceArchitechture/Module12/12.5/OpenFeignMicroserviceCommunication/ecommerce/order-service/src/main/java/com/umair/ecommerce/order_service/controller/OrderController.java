package com.umair.ecommerce.order_service.controller;


import com.umair.ecommerce.order_service.dto.OrderRequestDto;
import com.umair.ecommerce.order_service.entity.Order;
import com.umair.ecommerce.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/core")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/hello")
    public String helloFromOrders() {
        return "hello from orders";
    }

    @GetMapping
    public ResponseEntity<List<OrderRequestDto>> getAllOrders() {
        List<OrderRequestDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok().body(orders);

    }

    @GetMapping("/id")
    public ResponseEntity<OrderRequestDto> getOrderById(@RequestParam Long id) {

        OrderRequestDto order = orderService.getOrderById(id);
        return ResponseEntity.ok().body(order);

    }
}
