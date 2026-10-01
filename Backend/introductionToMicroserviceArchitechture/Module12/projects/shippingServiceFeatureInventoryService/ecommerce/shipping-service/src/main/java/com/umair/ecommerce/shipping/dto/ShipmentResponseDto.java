package com.umair.ecommerce.shipping.dto;

import com.umair.ecommerce.shipping.entity.enums.ShipmentStatus;

public record ShipmentResponseDto(

        Long shippingId,
        Long orderId,
        ShipmentStatus status,
        String trackingNumber

) {
}
