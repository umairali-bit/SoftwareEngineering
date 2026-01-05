package com.example.testing.testingapp.repositories;

import com.example.testing.testingapp.entities.Employee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;



@DataJpaTest
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .id(1L)
                .name("Walter White")
                .email("ww@example.com")
                .salary(80_000L)
                .build();
    }


    @Test
    void testFindByEmail_whenEmailIsPresent_thenReturnEmployee() {
 // Arrange

    employeeRepository.save(employee);



 // Act
        List<Employee> employeeList = employeeRepository.findByEmail(employee.getEmail());


 // Assert
        assertThat(employeeList).isNotNull();
        assertThat(employeeList).isNotEmpty();

        assertThat(employeeList.get(0).getEmail()).isEqualTo(employee.getEmail()); //going to be rolled back

        System.out.println(employeeList);

    }

    @Test
    void testFindByEmail_whenEmailIsNotValid_thenReturnEmptyEmployee() {

//  Arrange

        String email = "Invalid@example.com";



//  Act
        List<Employee> employeeList = employeeRepository.findByEmail(email);


//  Assert
        assertThat(employeeList).isNotNull();
        assertThat(employeeList).isEmpty();
        System.out.println(employeeList);

    }
}