package com.umair.ecommerce.order_service.client;

import com.umair.ecommerce.order_service.dto.OrderRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventory-service", path = "/inventory")
public interface InventoryFeignClient {

    @PutMapping("/products/reduced-stock")
    Double reduceStock(@RequestBody OrderRequestDto orderRequestDto);

}
