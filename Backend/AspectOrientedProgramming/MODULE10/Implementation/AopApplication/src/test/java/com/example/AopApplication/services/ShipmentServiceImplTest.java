package com.example.AopApplication.services;

import com.example.AopApplication.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void appEmployeeTestValidation() {

        Employee employee = new Employee();

        employee.setName("");
        employee.setEmail("bad-email");
        employee.setAge(15);

        assertThrows(IllegalArgumentException.class, () -> shipmentService.createEmployee(employee));




    }
}
