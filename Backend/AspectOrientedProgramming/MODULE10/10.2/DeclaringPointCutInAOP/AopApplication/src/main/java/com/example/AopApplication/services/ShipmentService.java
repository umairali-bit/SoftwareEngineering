package com.example.AopApplication.services;

public interface ShipmentService {

    String orderPackage (Long orderId);
    String trackPackage (Long trackId);
}
