package com.umair.ecommerce.order_service.service;

import com.umair.ecommerce.order_service.OrderRepository.OrderRepository;
import com.umair.ecommerce.order_service.dto.OrderRequestDto;
import com.umair.ecommerce.order_service.entity.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    public List<OrderRequestDto> getAllOrders() {

        log.info("getAllOrders()");

        List<Order> orders = orderRepository.findAll();

        return orders.stream()
                .map(item -> modelMapper.map(item, OrderRequestDto.class))
                .toList();
    }

    public OrderRequestDto getOrderById(Long id) {
        log.info("getOrderById({})", id);
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));

        return modelMapper.map(order, OrderRequestDto.class);
    }
}
