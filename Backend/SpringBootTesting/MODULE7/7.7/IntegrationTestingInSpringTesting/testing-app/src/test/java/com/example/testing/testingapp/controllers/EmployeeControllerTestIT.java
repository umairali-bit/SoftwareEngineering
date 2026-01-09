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



class EmployeeControllerTestIT extends AbstractIntegrationTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
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

    @Test
    void testUpdateEmployee_IfEmployeeDoesNotExists_ThrowException() {
        webTestClient.put()
                .uri("/employees/222")
                .bodyValue(employeeDto)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testUpdateEmployee_whenUpdatingEmail_ThrowException() {
        Employee savedEmployee = employeeRepository.save(employee);

        employeeDto.setName("Random");
        employeeDto.setEmail("random@xyz.com");

        webTestClient.put()
                .uri("/employees/{id}", savedEmployee.getId())
                .bodyValue(employeeDto)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void testUpdateEmployee_whenEmployeeIsValid_UpdateEmployee() {
        Employee savedEmployee = employeeRepository.save(employee);
        employeeDto.setName("Random");
        employeeDto.setSalary(93_000L);

        webTestClient.put()
                .uri("/employees/{id}", savedEmployee.getId())
                .bodyValue(employeeDto)
                .exchange()
                .expectStatus().isOk()
//             checking if everything was updated
                .expectBody(EmployeeDto.class)
                .isEqualTo(employeeDto);


    }

    @Test
    void testDeleteEmployee_whenEmployeeDoesNotExist_ThrowException() {
        webTestClient.delete()
                .uri("/employees/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testDeleteEmployee_whenEmployeeIsValid_DeleteEmployee() {
        Employee savedEmployee = employeeRepository.save(employee);

        webTestClient.delete()
                .uri("/employees/{id}", savedEmployee.getId())
                .exchange()
                .expectStatus().isNoContent()
                .expectBody(Void.class);

//        checking if id is actually deleted
        webTestClient.delete()
                .uri("/employees/{id}", savedEmployee.getId())
                .exchange()
                .expectStatus().isNotFound();

    }


}