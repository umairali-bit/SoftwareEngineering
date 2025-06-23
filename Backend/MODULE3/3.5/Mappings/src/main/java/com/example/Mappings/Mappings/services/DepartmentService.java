package com.example.Mappings.Mappings.services;


import com.example.Mappings.Mappings.entities.DepartmentEntity;
import com.example.Mappings.Mappings.entities.EmployeeEntity;
import com.example.Mappings.Mappings.repositories.DepartmentRepository;
import com.example.Mappings.Mappings.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;



    public DepartmentService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;

        this.employeeRepository = employeeRepository;
    }

    public DepartmentEntity createNewDepartment (DepartmentEntity departmentEntity) {

        return departmentRepository.save(departmentEntity);
    }


    public DepartmentEntity getDepartmentById (Long DepartId) {
        return departmentRepository.findById(DepartId).orElse(null);
    }

    public DepartmentEntity assignManager(Long departmentId, Long employeeId) {
        Optional<DepartmentEntity> departmentEntity = departmentRepository.findById(departmentId);
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(employeeId);

        return departmentEntity
                .flatMap(department ->
                        employeeEntity.map(employee -> {
                            department.setManager(employee);
                            return departmentRepository.save(department);
                        })
                )
                .orElse(null);


    }

    public DepartmentEntity getAssignedManager(Long employeeID) {
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(employeeID);
        return employeeEntity
                .map(employee -> employee.getManagedDepartment())
                .orElse(null);

    }

}








