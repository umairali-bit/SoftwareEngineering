package com.example.inputvalidation.inputvalidation.repositories;
import com.example.inputvalidation.inputvalidation.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    


}
