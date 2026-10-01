package com.umair.ecommerce.order_service.service;

import com.umair.ecommerce.order_service.OrderRepository.OrderRepository;
import com.umair.ecommerce.order_service.client.InventoryFeignClient;
import com.umair.ecommerce.order_service.client.ShipmentFeignClient;
import com.umair.ecommerce.order_service.dto.OrderRequestDto;
import com.umair.ecommerce.order_service.dto.ShipmentRequestDto;
import com.umair.ecommerce.order_service.dto.ShipmentResponseDto;
import com.umair.ecommerce.order_service.entity.Order;
import com.umair.ecommerce.order_service.entity.OrderItem;
import com.umair.ecommerce.order_service.entity.enums.OrderStatus;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final InventoryFeignClient inventoryFeignClient;
    private final ShipmentFeignClient  shipmentFeignClient;

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

        ShipmentRequestDto shipmentRequestDto = new ShipmentRequestDto(savedOrder.getId());
        ShipmentResponseDto shipmentResponseDto = shipmentFeignClient.createShipment(shipmentRequestDto);

        log.info("created shipment response: {}", shipmentResponseDto);
        return modelMapper.map(savedOrder, OrderRequestDto.class);

    }

    public OrderRequestDto createOrderFallback(OrderRequestDto orderRequestDto, Throwable throwable) {
        log.error("Fallback error due to: {}", throwable.getMessage());

        return new  OrderRequestDto();



    }
    @Transactional
    public OrderRequestDto cancelOrders(Long orderId) {
        log.info("cancelOrders({})", orderId);
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new RuntimeException("Order not found"));

        log.info("Order found: {}", order);
        log.info("Order items: {}", order.getOrderItems());

        if(order.getOrderStatus() == OrderStatus.CANCELED){
            throw new RuntimeException("Order cancelled");
        }

        OrderRequestDto orderRequestDto = modelMapper.map(order, OrderRequestDto.class);
        log.info("Mapped DTO: {}", orderRequestDto);
        log.info("DTO items: {}", orderRequestDto.getItems());

        inventoryFeignClient.restock(orderRequestDto);

        log.info("Inventory successfully restocked");

        order.setOrderStatus(OrderStatus.CANCELED);

        return modelMapper.map(order, OrderRequestDto.class);
    }


}
