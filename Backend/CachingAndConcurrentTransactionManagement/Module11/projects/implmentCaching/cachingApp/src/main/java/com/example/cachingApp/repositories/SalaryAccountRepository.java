package com.example.cachingApp.repositories;


import com.example.cachingApp.entities.EmployeeEntity;
import com.example.cachingApp.entities.SalaryAccountEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SalaryAccountRepository extends CrudRepository<SalaryAccountEntity, Long> {
//    boolean existsByEmployee(EmployeeEntity employee);

//    @Override //cant do findByEmployee in CurdRepository
    Optional<SalaryAccountEntity> findByEmployee(EmployeeEntity employee);

    @Override
//    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<SalaryAccountEntity> findById(Long id);

}
