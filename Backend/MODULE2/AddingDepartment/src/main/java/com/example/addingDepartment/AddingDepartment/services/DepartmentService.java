package com.example.addingDepartment.AddingDepartment.services;

import com.example.addingDepartment.AddingDepartment.repositories.DepartmentRepository;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    private final DepartmentRepository repository;
    private final ModelMapper modelMapper;


    public DepartmentService(DepartmentRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }


}
