package com.example.testing.testingapp.services;

import com.example.testing.testingapp.TestContainerConfiguration;
import com.example.testing.testingapp.dto.EmployeeDto;
import com.example.testing.testingapp.entities.Employee;
import com.example.testing.testingapp.repositories.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@Import(TestContainerConfiguration.class)
//@DataJpaTest (only for repositories)
//@SpringBootTest (for integration testing)
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeServiceImplTest;


    @Test
    void testGetEmployeeById_WhenEmployeeIdIsPresent_ThenReturnEmployeeDto() {
        employeeServiceImplTest.getEmployeeById(1L);

        // assign
        Long id = 1L;
        Employee mockEmployee = Employee.builder()
                .id(id)
                .email("mockEmployee@xyz.com")
                .name("mockEmployee")
                .salary(80_000L)
                .build();

        when(employeeRepository.findById(id)).thenReturn(Optional.of(mockEmployee)); // stubbing
    }



}