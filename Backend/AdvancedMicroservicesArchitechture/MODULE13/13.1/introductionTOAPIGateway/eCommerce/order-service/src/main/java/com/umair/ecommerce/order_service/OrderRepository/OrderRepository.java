package com.umair.ecommerce.order_service.OrderRepository;


import com.umair.ecommerce.order_service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
