package com.umair.ecommerce.order_service.service;

import com.umair.ecommerce.order_service.OrderRepository.OrderRepository;
import com.umair.ecommerce.order_service.client.InventoryFeignClient;
import com.umair.ecommerce.order_service.dto.OrderRequestDto;
import com.umair.ecommerce.order_service.entity.Order;
import com.umair.ecommerce.order_service.entity.OrderItem;
import com.umair.ecommerce.order_service.entity.enums.OrderStatus;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
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
    private final InventoryFeignClient inventoryFeignClient;

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

//    @Retry(name = "inventoryRetry", fallbackMethod = "createOrderFallback")
    @CircuitBreaker(name = "inventoryCircuitBreaker", fallbackMethod = "createOrderFallback")
//    @RateLimiter(name = "inventoryRateLimiter", fallbackMethod = "createOrderFallback")
    public OrderRequestDto createOrders(OrderRequestDto orderRequestDto) {

        log.info("creating orders ({})", orderRequestDto);
        Double totalPrice = inventoryFeignClient.reduceStock(orderRequestDto);

        Order orders = modelMapper.map(orderRequestDto, Order.class);
        for(OrderItem orderItem : orders.getOrderItems()){
            orderItem.setOrder(orders);
        }
        orders.setTotalPrice(totalPrice);
        orders.setOrderStatus(OrderStatus.CONFIRMED);

        Order savedOrder = orderRepository.save(orders);
        return modelMapper.map(savedOrder, OrderRequestDto.class);

    }

    public OrderRequestDto createOrderFallback(OrderRequestDto orderRequestDto, Throwable throwable) {
        log.error("Fallback error due to: {}", throwable.getMessage());

        return new  OrderRequestDto();



    }


}
