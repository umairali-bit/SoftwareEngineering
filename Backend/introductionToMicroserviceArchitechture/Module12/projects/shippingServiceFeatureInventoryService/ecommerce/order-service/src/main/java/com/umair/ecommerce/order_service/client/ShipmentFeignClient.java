package com.umair.ecommerce.order_service.client;


import com.umair.ecommerce.order_service.dto.ShipmentRequestDto;
import com.umair.ecommerce.order_service.dto.ShipmentResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "shipping-service", path = "/shipping")
public interface ShipmentFeignClient {

    @PostMapping("/shipments/create-shipment")
    ShipmentResponseDto createShipment(ShipmentRequestDto request);
}
