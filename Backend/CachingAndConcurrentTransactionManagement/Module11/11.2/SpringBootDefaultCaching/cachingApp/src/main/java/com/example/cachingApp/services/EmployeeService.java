package com.example.cachingApp.services;


import com.example.cachingApp.dtos.EmployeeDTO;
import com.example.cachingApp.entities.EmployeeEntity;
import com.example.cachingApp.exceptions.ResourceNotFoundException;
import com.example.cachingApp.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final ModelMapper modelMapper;

//    public void existsByEmployeeId(Long employeeId) {
//
//        boolean exists = employeeRepository.existsById(employeeId);
//        if (!exists) {
//            throw new ResourceNotFoundException("Employee with id: " + employeeId + " does not exist");
//        }
//    }

    public EmployeeDTO getEmployeeById(Long employeeId) {

//        existsByEmployeeId(employeeId);
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee with id: " + employeeId + " not found")
        );

        return modelMapper.map(employeeEntity, EmployeeDTO.class);


    }

    public List<EmployeeDTO> getAllEmployees() {

        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        return employeeEntities
                .stream()
                .map(i-> modelMapper.map(i, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO, EmployeeEntity.class);

        employeeEntity = employeeRepository.save(employeeEntity);
        return modelMapper.map(employeeEntity, EmployeeDTO.class);
    }

    public boolean  deleteEmployee(Long employeeId) {

        if(employeeRepository.findById(employeeId).isPresent()) {
            employeeRepository.deleteById(employeeId);
            return true;

        } throw new ResourceNotFoundException("Employee with id: " + employeeId + " not found");

    }




}
