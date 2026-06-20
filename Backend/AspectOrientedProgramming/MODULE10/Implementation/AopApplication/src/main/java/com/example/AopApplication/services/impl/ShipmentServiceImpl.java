package com.example.AopApplication.services.impl;

import com.example.AopApplication.services.ShipmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.AopApplication.aspects.MyLogging;

@Service
@Slf4j
public class ShipmentServiceImpl implements ShipmentService {

    @Override
    @MyLogging
    public String orderPackage(Long orderId) {
        try{
            log.info("Processing the order...");
            Thread.sleep(1000);
        } catch (Exception e) {
            log.error("Error processing the order.", e);
        }
        return "The order has been processed successfully, orderId: " + orderId;
    }

    @Override
    @Transactional
    public String trackPackage(Long trackId) {
        try{
            log.info("Processing the track...");
            Thread.sleep(500);
            throw new RuntimeException("Exception occurred during tracking the package");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
