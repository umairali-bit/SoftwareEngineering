package com.example.cachingApp.services;

import com.example.cachingApp.dtos.SalaryAccountDTO;
import com.example.cachingApp.entities.EmployeeEntity;

public interface SalaryAccountService {


    void createAccount(EmployeeEntity employeeEntity);

    SalaryAccountDTO implementBalance(Long accountId);
}
