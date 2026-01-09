package com.example.testing.testingapp.controllers;

import com.example.testing.testingapp.TestContainerConfiguration;
import com.example.testing.testingapp.dto.EmployeeDto;
import com.example.testing.testingapp.entities.Employee;
import com.example.testing.testingapp.repositories.EmployeeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@AutoConfigureWebTestClient(timeout = "100000")
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

        employeeRepository.deleteAll();
    }

    @Test
    void testGetEmployeeById_Success() {

        Employee savedEmployee = employeeRepository.save(employee);

        webTestClient.get()
                .uri("/employees/{id}", savedEmployee.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(EmployeeDto.class)
//                .isEqualTo(employeeDto);
                .value(employeeDto -> {
                   assertThat(employeeDto.getEmail()).isEqualTo(savedEmployee.getEmail());
                   assertThat(employeeDto.getId()).isEqualTo(savedEmployee.getId());
                });




    }

    @Test
    void testGetEmployeeById_Failure() {
        webTestClient.get()
                .uri("/employees/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testCreateEmployee_IfEmployeeExists_ThrowException() {
        Employee savedEmployee = employeeRepository.save(employee);

        webTestClient.post()
                .uri("/employees")
                .bodyValue(employeeDto)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void testCreateEmployee() {
        webTestClient.post()
                .uri("/employees")
                .bodyValue(employeeDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.email").isEqualTo(employee.getEmail())
                .jsonPath("$.name").isEqualTo(employee.getName());

    }







}