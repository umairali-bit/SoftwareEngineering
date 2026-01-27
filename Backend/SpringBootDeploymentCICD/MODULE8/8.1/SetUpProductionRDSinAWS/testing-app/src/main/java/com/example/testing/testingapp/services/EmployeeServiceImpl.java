package com.example.testing.testingapp.services;

import com.example.testing.testingapp.dto.EmployeeDto;
import com.example.testing.testingapp.entities.Employee;
import com.example.testing.testingapp.exceptions.ResourceNotFoundException;
import com.example.testing.testingapp.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;


    private final ModelMapper modelMapper;


    @Override
    public EmployeeDto getEmployeeById(Long id) {
        log.info("Fetching employee with id: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Employee not found with id: {}", id);
                    return new ResourceNotFoundException("Employee not found with id: " + id);
                });
        log.info("Successfully fetched employee with id: {}", id);
        return modelMapper.map(employee, EmployeeDto.class);
    }

    @Override
    public EmployeeDto createNewEmployee(EmployeeDto employeeDto) {
        log.info("Creating new employee with email: {}", employeeDto.getEmail());
        List<Employee> existingEmployees = employeeRepository.findByEmail(employeeDto.getEmail());

        if (!existingEmployees.isEmpty()) {
            log.error("Employee already exists with email: {}", employeeDto.getEmail());
            throw new RuntimeException("Employee already exists with email: " + employeeDto.getEmail());
        }
        Employee newEmployee = modelMapper.map(employeeDto, Employee.class);
        Employee savedEmployee = employeeRepository.save(newEmployee);
        log.info("Successfully created new employee with id: {}", savedEmployee.getId());
        return modelMapper.map(savedEmployee, EmployeeDto.class);
    }

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        log.info("Updating employee with id: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Employee not found with id: {}", id);
                    return new ResourceNotFoundException("Employee not found with id: " + id);
                });

        if (!employee.getEmail().equals(employeeDto.getEmail())) {
            log.error("Attempted to update email for employee with id: {}", id);
            throw new RuntimeException("The email of the employee cannot be updated");
        }

        modelMapper.map(employeeDto, employee);
        employee.setId(id);

        Employee savedEmployee = employeeRepository.save(employee);
        log.info("Successfully updated employee with id: {}", id);
        return modelMapper.map(savedEmployee, EmployeeDto.class);
    }

    @Override
    public void deleteEmployee(Long id) {
        log.info("Deleting employee with id: {}", id);
        boolean exists = employeeRepository.existsById(id);
        if (!exists) {
            log.error("Employee not found with id: {}", id);
            throw new ResourceNotFoundException("Employee not found with id: " + id);
        }

        employeeRepository.deleteById(id);
        log.info("Successfully deleted employee with id: {}", id);
    }
}

/*
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    // ---------- Mapping helpers ----------
    // Entity -> DTO
    private EmployeeDto mapToDto(Employee employee) {
        EmployeeDto dto = new EmployeeDto();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setEmail(employee.getEmail());
        dto.setSalary(employee.getSalary());
        return dto;
    }

    // DTO -> Entity (for create/update)
    private void mapToEntity(EmployeeDto dto, Employee employee) {
        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setSalary(dto.getSalary());
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {

        // 1) Fetch from DB
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));

        // 2) Convert Entity -> DTO and return
        return mapToDto(employee);
    }

    @Override
    public EmployeeDto createNewEmployee(EmployeeDto employeeDto) {

        // 1) Create a new Entity from incoming DTO
        Employee employee = new Employee();
        mapToEntity(employeeDto, employee);

        // 2) Save to DB (DB will generate the ID)
        Employee savedEmployee = employeeRepository.save(employee);

        // 3) Return saved Entity as DTO
        return mapToDto(savedEmployee);
    }

    @Override
    public EmployeeDto updateEmployeeById(Long id, EmployeeDto employeeDto) {

        // 1) Fetch existing Entity
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));

        // 2) Business rule (your rule): name must match
        if (!employee.getName().equals(employeeDto.getName())) {
            throw new RuntimeException("Employee name does not match");
        }

        // 3) Update allowed fields from DTO -> existing Entity
        // (name not changed because of your rule)
        employee.setEmail(employeeDto.getEmail());
        employee.setSalary(employeeDto.getSalary());

        // 4) Save updated Entity
        Employee savedEmployee = employeeRepository.save(employee);

        // 5) Return updated DTO
        return mapToDto(savedEmployee);
    }

    @Override
    public void deleteEmployeeById(Long id) {

        // 1) Ensure employee exists
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));

        // 2) Delete
        employeeRepository.delete(employee);
    }
}
 */
