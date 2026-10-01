package com.umair.ecommerce.order_service.dto;


public record ShipmentResponseDto(

        Long shippingId,
        Long orderId,
        String status,
        String trackingNumber

) {
}
