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
//     Entity to DTO
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setName(employee.getName());
        employeeDto.setEmail(employee.getEmail());
        employeeDto.setSalary(employee.getSalary());

        return employeeDto;

        //return ModelMapper.map(employee, EmployeeDTO.class)


    }

    @Override
    public EmployeeDto createNewEmployee(EmployeeDto dto) {
        Employee employee = new Employee();


//     DTO to Entity
        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setSalary(dto.getSalary());


        Employee savedEmployee = employeeRepository.save(employee);

//     Entity to DTO
        dto.setId(savedEmployee.getId());
        dto.setName(savedEmployee.getName());
        dto.setEmail(savedEmployee.getEmail());
        dto.setSalary(savedEmployee.getSalary());

        return dto;
    }

    @Override
    public EmployeeDto updateEmployeeById(Long id, EmployeeDto employeeDto) {
        Employee employee  = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));

        if(!employee.getName().equals(employeeDto.getName())) {
            throw new RuntimeException("Employee name does not match");
        }

        employee.setEmail(employeeDto.getEmail());
        employee.setSalary(employeeDto.getSalary());

        Employee savedEmployee = employeeRepository.save(employee);

//     Entity to DTO
        EmployeeDto dto = new EmployeeDto();
        dto.setId(savedEmployee.getId());
        dto.setName(savedEmployee.getName());
        dto.setEmail(savedEmployee.getEmail());
        dto.setSalary(savedEmployee.getSalary());


        return dto;
    }

    @Override
    public void deleteEmployeeById(Long id) {
        Employee employee  = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));

        employeeRepository.delete(employee);

    }
}
