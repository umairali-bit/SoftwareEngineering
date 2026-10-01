package com.umair.ecommerce.shipping.service;

import com.umair.ecommerce.shipping.dto.ShipmentRequestDto;
import com.umair.ecommerce.shipping.dto.ShipmentResponseDto;

public interface ShipmentService {

    ShipmentResponseDto createShipment(ShipmentRequestDto request);

    ShipmentResponseDto getShippingStatus(Long orderId);
}
