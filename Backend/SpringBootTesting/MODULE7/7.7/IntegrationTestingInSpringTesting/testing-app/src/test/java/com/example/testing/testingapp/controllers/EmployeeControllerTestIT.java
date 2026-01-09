package com.example.testing.testingapp.controllers;

import com.example.testing.testingapp.TestContainerConfiguration;
import com.example.testing.testingapp.dto.EmployeeDto;
import com.example.testing.testingapp.entities.Employee;
import com.example.testing.testingapp.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.security.PrivateKey;


@AutoConfigureWebTestClient(timeout = "100000L")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestContainerConfiguration.class)
class EmployeeControllerTestIT {

    @Spy
    private ModelMapper modelMapper;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;
    private EmployeeDto employeeDto;
    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .id(1L)
                .name("Walter White")
                .salary(90_000L)
                .email("ww@xyz.com")
                .build();

        employeeDto = modelMapper.map(employee, EmployeeDto.class);
    }

    @Test
    void testGetEmployeeById_Success() {



    }



}