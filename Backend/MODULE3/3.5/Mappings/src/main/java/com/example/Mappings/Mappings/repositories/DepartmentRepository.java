package com.example.Mappings.Mappings.repositories;

import com.example.Mappings.Mappings.entities.DepartmentEntity;
import com.example.Mappings.Mappings.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {

    DepartmentEntity findByManager(EmployeeEntity employeeEntity);
}
