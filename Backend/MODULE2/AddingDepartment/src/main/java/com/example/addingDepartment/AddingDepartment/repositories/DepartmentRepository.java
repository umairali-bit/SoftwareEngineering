package com.example.addingDepartment.AddingDepartment.repositories;


import com.example.addingDepartment.AddingDepartment.entities.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long>{

}
