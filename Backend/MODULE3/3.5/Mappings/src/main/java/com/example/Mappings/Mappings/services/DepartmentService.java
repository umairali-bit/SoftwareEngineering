package com.example.Mappings.Mappings.services;


import com.example.Mappings.Mappings.entities.DepartmentEntity;
import com.example.Mappings.Mappings.repositories.DepartmentRepository;
import com.example.Mappings.Mappings.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;



    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;

    }

    public DepartmentEntity createNewDepartment (DepartmentEntity departmentEntity) {

        return departmentRepository.save(departmentEntity);
    }


    public DepartmentEntity getDepartmentById (Long DepartId) {
        return departmentRepository.findById(DepartId).orElse(null);
    }
}








