package com.example.cachingApp.services.impl;


import com.example.cachingApp.dtos.EmployeeDTO;
import com.example.cachingApp.entities.EmployeeEntity;
import com.example.cachingApp.exceptions.ResourceNotFoundException;
import com.example.cachingApp.repositories.EmployeeRepository;
import com.example.cachingApp.services.EmployeeService;
import com.example.cachingApp.services.SalaryAccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final SalaryAccountService salaryAccountService;

    private final ModelMapper modelMapper;

    private final String CACHE_NAME = "employees";

//    public void existsByEmployeeId(Long employeeId) {
//
//        boolean exists = employeeRepository.existsById(employeeId);
//        if (!exists) {
//            throw new ResourceNotFoundException("Employee with id: " + employeeId + " does not exist");
//        }
//    }
    @Override
    @Cacheable(cacheNames = CACHE_NAME, key = "#employeeId")
    public EmployeeDTO getEmployeeById(Long employeeId) {

//        existsByEmployeeId(employeeId);
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee with id: " + employeeId + " not found")
        );


//        return modelMapper.map(employeeEntity, EmployeeDTO.class);
//        records dont fit well with model mapper cuz records are immutable
//        they do not provide no args constructor or setters

        return new EmployeeDTO(
                employeeEntity.getId(),
                employeeEntity.getName(),
                employeeEntity.getEmail(),
                employeeEntity.getAge(),
                employeeEntity.getRole(),
                employeeEntity.getBirthDate(),
                employeeEntity.isActive(),
                employeeEntity.getSalary()
        );


    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {

        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        return employeeEntities
                .stream()
                .map(i-> modelMapper.map(i, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @CachePut(cacheNames = CACHE_NAME, key = "#result.id")
    @Transactional
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {


        EmployeeEntity employee = new EmployeeEntity();

        employee.setName(employeeDTO.name());
        employee.setEmail(employeeDTO.email());
        employee.setAge(employeeDTO.age());
        employee.setRole(employeeDTO.role());
        employee.setBirthDate(employeeDTO.birthDate());
        employee.setActive(employeeDTO.isActive());
        employee.setSalary(employeeDTO.salary());

        employee = employeeRepository.save(employee);

        salaryAccountService.createAccount(employee);

        return new EmployeeDTO(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getAge(),
                employee.getRole(),
                employee.getBirthDate(),
                employee.isActive(),
                employee.getSalary()
        );


    }

    @Override
    @CacheEvict(cacheNames = CACHE_NAME, key = "#employeeId")
    public void deleteEmployee(Long employeeId) {

        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee with id: " + employeeId + " not found"));

        employeeRepository.delete(employee);
    }




}
