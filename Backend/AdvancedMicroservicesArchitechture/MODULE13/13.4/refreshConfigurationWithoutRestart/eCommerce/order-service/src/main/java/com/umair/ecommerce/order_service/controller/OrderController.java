package com.umair.ecommerce.order_service.controller;


import com.umair.ecommerce.order_service.config.FeaturesEnableConfig;
import com.umair.ecommerce.order_service.dto.OrderRequestDto;
import com.umair.ecommerce.order_service.entity.Order;
import com.umair.ecommerce.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/core")
@RefreshScope
public class OrderController {

    @Value("${my.variable}")
    private String myVariable;

    @GetMapping("/config")
    public String getConfig() {
        return myVariable;
    }

    private final OrderService orderService;
    private final FeaturesEnableConfig featuresEnableConfig;



    @GetMapping("/hello")
    public String helloFromOrders(@RequestHeader("X-User-Id") Long userId) {

        if (featuresEnableConfig.isTrackingEnabled()) {
            return "User tracking enabled";
        } else {
            return "User not tracking enabled";
        }

//        return "hello from orders. The user id is " + userId;
    }

    @PostMapping("/create-order")
    public ResponseEntity<OrderRequestDto> createOrder(@RequestBody OrderRequestDto orderRequestDto) {

        OrderRequestDto orders = orderService.createOrders(orderRequestDto);
        return ResponseEntity.ok(orders);


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
