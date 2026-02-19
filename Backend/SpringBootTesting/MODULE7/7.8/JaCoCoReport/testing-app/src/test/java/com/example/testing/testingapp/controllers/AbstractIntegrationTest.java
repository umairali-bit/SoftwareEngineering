package com.example.testing.testingapp.controllers;


import com.example.testing.testingapp.TestContainerConfiguration;
import com.example.testing.testingapp.dto.EmployeeDto;
import com.example.testing.testingapp.entities.Employee;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient(timeout = "100000")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestContainerConfiguration.class)
public abstract class AbstractIntegrationTest {

    @Autowired
    WebTestClient webTestClient;


    Employee employee = Employee.builder()
            .id(1L)
            .email("mockEmployee@xyz.com")
            .name("mockEmployee")
            .salary(80_000L)
            .build();

   EmployeeDto employeeDto = EmployeeDto.builder()
           .id(employee.getId()).
           name(employee.getName()).
           salary(employee.getSalary()).
           email(employee.getEmail()).build();

}
