package com.umair.ecommerce.inventory_service.dto;

import lombok.Data;

@Data
public class OrderItemRequestDto {

    private Long id;
    private Long productId;
    private Integer quantity;


}
