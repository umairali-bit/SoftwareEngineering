package com.example.cachingApp.repositories;


import com.example.cachingApp.entities.SalaryAccountEntity;
import org.springframework.data.repository.CrudRepository;

public interface SalaryAccountRepository extends CrudRepository<SalaryAccountEntity, Integer> {

}
