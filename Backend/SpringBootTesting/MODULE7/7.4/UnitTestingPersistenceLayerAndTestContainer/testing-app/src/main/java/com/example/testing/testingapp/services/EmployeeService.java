package com.example.testing.testingapp.services;

import com.example.testing.testingapp.dto.EmployeeDto;

public interface EmployeeService {

    EmployeeDto getEmployeeById(Long id);
    EmployeeDto createNewEmployee(EmployeeDto employeeDto);
    EmployeeDto updateEmployeeById(Long id, EmployeeDto employeeDto);
    void deleteEmployeeById(Long id);

}
