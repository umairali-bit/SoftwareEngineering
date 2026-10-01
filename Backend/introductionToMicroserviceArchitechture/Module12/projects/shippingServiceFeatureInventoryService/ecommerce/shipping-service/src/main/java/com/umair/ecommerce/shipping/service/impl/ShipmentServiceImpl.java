package com.umair.ecommerce.shipping.service.impl;

import com.umair.ecommerce.shipping.dto.ShipmentRequestDto;
import com.umair.ecommerce.shipping.dto.ShipmentResponseDto;
import com.umair.ecommerce.shipping.entity.Shipment;
import com.umair.ecommerce.shipping.entity.enums.ShipmentStatus;
import com.umair.ecommerce.shipping.repository.ShipmentRepository;
import com.umair.ecommerce.shipping.service.ShipmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;


    @Override
    public ShipmentResponseDto createShipment(ShipmentRequestDto request) {
        log.info("creating shipment for orderId: {}", request.orderId());

        Shipment shipment = new Shipment();
        shipment.setOrderId(request.orderId());
        shipment.setShipmentStatus(ShipmentStatus.PENDING);
        shipment.setTrackingNumber(UUID.randomUUID().toString());

        Shipment savedShipment = shipmentRepository.save(shipment);

        return new ShipmentResponseDto(
                savedShipment.getShippingId(),
                savedShipment.getOrderId(),
                savedShipment.getShipmentStatus(),
                savedShipment.getTrackingNumber()
        );
    }

    @Override
    public ShipmentResponseDto getShippingStatus(Long orderId) {

        Shipment shipment = shipmentRepository.findByOrderId(orderId).orElseThrow(
                () -> new RuntimeException("shipment not found"));

        return new ShipmentResponseDto(
                shipment.getShippingId(),
                shipment.getOrderId(),
                shipment.getShipmentStatus(),
                shipment.getTrackingNumber()
        );
    }
}
