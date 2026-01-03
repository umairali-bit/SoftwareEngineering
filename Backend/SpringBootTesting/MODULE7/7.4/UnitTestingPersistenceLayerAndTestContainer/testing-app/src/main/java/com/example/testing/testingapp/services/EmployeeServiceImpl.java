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
