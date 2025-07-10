package com.exceptionHandelingInSpringBoot.repositories;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface EmployeeRepository extends JpaRepository<com.exceptionHandelingInSpringBoot.entities
        .EmployeeEntity, Long> {
    
}
