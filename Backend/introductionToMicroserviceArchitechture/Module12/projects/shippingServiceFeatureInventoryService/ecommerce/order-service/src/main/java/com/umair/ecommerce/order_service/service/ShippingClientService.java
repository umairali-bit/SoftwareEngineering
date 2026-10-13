package com.umair.ecommerce.order_service.service;


import com.umair.ecommerce.order_service.client.ShipmentFeignClient;
import com.umair.ecommerce.order_service.dto.ShipmentRequestDto;
import com.umair.ecommerce.order_service.dto.ShipmentResponseDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShippingClientService {

    private final ShipmentFeignClient  shipmentFeignClient;

    @Retry(
            name = "shippingRetry",
            fallbackMethod = "shippingFallback"
    )

    @CircuitBreaker(
            name = "shippingCircuitBreaker"
//            fallbackMethod = "shippingFallback"
    )
    public ShipmentResponseDto createShipment(ShipmentRequestDto shipmentRequestDto) {

        log.info("creating shipment for the orderId: {}", shipmentRequestDto.orderId());

        return shipmentFeignClient.createShipment(shipmentRequestDto);


    }

    public ShipmentResponseDto shippingFallback(
            ShipmentRequestDto shipmentRequestDto,
            Throwable throwable

    ) {
        log.error(
                "Shipping Service unavailable for orderId {}: {}",
                shipmentRequestDto.orderId(),
                throwable.getMessage()
        );

        throw new RuntimeException(
                "Unable to create shipment. Shipping Service is unavailable.",
                throwable
        );
    }
}
