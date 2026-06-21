package com.example.AopApplication.services;

import com.example.AopApplication.entity.Employee;

public interface ShipmentService {

    String orderPackage (Long orderId);
    String trackPackage (Long trackId);
    String createEmployee(Employee employee);
}
