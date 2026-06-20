package com.example.AopApplication.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
public class ShipmentServiceImplTest {

    @Autowired
    private ShipmentService shipmentService;

    @Test
//    @WithMockUser(roles ="USER") - gives the exception
    @WithMockUser(roles = "ADMIN")
    void appTestOrderPackage() {
        shipmentService.orderPackage(4L);
    }

    @Test
    void appTestTrackPackage() {
        shipmentService.trackPackage(4L);
    }
}
