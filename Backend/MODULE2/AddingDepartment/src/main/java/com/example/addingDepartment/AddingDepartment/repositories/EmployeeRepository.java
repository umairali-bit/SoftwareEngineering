package com.example.addingDepartment.AddingDepartment.repositories;

import com.example.addingDepartment.AddingDepartment.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
}