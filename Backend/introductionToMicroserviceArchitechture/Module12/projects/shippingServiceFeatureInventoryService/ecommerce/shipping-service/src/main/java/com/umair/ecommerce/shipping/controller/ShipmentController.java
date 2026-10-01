package com.umair.ecommerce.shipping.controller;

import com.umair.ecommerce.shipping.dto.ShipmentRequestDto;
import com.umair.ecommerce.shipping.dto.ShipmentResponseDto;
import com.umair.ecommerce.shipping.entity.Shipment;
import com.umair.ecommerce.shipping.service.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("shipments")
public class ShipmentController {

    private final ShipmentService shipmentService;


    @PostMapping("create-shipment")
    public ResponseEntity<ShipmentResponseDto> createShipment(@RequestBody ShipmentRequestDto shipment){

        return ResponseEntity.ok(shipmentService.createShipment(shipment));
    }

    @GetMapping("order/shipment")
    public ResponseEntity<ShipmentResponseDto> getShipment(@RequestParam Long orderId){


        ShipmentResponseDto shipment = shipmentService.getShippingStatus(orderId);
        return ResponseEntity.ok(shipment);
    }
}
