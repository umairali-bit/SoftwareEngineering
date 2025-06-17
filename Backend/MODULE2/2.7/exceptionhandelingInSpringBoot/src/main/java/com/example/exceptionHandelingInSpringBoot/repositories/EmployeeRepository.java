package com.example.exceptionHandelingInSpringBoot.repositories;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface EmployeeRepository extends JpaRepository<com.example.exceptionHandelingInSpringBoot.entities
        .EmployeeEntity, Long> {
    
}
