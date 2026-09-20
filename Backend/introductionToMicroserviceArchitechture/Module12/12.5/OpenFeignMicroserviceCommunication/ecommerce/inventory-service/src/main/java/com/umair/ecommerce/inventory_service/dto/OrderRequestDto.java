package com.umair.ecommerce.inventory_service.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderRequestDto {
    private Long id;
    private List<OrderItemRequestDto> items;
    private BigDecimal totalPrice;


}
