package com.example.Mappings.Mappings.services;


import com.example.Mappings.Mappings.entities.EmployeeEntity;
import com.example.Mappings.Mappings.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;


    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public EmployeeEntity createNewEmployee (EmployeeEntity employeeEntity) {
        return employeeRepository.save(employeeEntity);
    }

    public EmployeeEntity getEmployeeById (Long EmployeeID) {
        return employeeRepository.findById(EmployeeID).orElse(null);
    }


}
