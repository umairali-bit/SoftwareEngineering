package com.example.AopApplication.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class ShipmentServiceImplTest {

    @Autowired
    private ShipmentService shipmentService;

    @Test
    void appTestOrderPackage() {

        String orderInfo = shipmentService.orderPackage(4L);
        log.info("orderInfo:{}", orderInfo);
    }

    @Test
    void appTestTrackPackage() {
        shipmentService.trackPackage(4L);
    }
}
