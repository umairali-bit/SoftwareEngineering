package com.example.cachingApp.services;

import com.example.cachingApp.dtos.EmployeeDTO;


import java.util.List;

public interface EmployeeService {

    EmployeeDTO getEmployeeById(Long employeeId);
    List<EmployeeDTO> getAllEmployees();
    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);
    void deleteEmployee(Long employeeId);


}
