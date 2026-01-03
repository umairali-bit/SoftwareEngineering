package com.example.testing.testingapp.services;

import com.example.testing.testingapp.dto.EmployeeDto;
import com.example.testing.testingapp.entities.Employee;
import com.example.testing.testingapp.exceptions.ResourceNotFoundException;
import com.example.testing.testingapp.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;


    @Override
    public EmployeeDto getEmployeeById(Long id) {
        Employee employee  = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employeeDto.getId());
        employeeDto.setName(employeeDto.getName());
        employeeDto.setEmail(employeeDto.getEmail());
        employeeDto.setSalary(employeeDto.getSalary());

        return employeeDto;

        //return ModelMapper.map(employee, EmployeeDTO.class)


    }

    @Override
    public EmployeeDto createNewEmployee(EmployeeDto employeeDto) {
        return null;
    }

    @Override
    public EmployeeDto updateEmployeeById(Long id, EmployeeDto employeeDto) {
        return null;
    }

    @Override
    public void deleteEmployeeById(Long id) {

    }
}
