package com.example.cachingApp.repositories;


import com.example.cachingApp.entities.EmployeeEntity;
import com.example.cachingApp.entities.SalaryAccountEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SalaryAccountRepository extends CrudRepository<SalaryAccountEntity, Long> {
//    boolean existsByEmployee(EmployeeEntity employee);

    Optional<SalaryAccountEntity> findByEmployee(EmployeeEntity employee);

}
