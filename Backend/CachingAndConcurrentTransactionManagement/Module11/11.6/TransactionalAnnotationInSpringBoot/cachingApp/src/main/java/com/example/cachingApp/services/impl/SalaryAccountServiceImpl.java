package com.example.cachingApp.services.impl;

import com.example.cachingApp.entities.EmployeeEntity;
import com.example.cachingApp.entities.SalaryAccountEntity;
import com.example.cachingApp.repositories.SalaryAccountRepository;
import com.example.cachingApp.services.SalaryAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class SalaryAccountServiceImpl implements SalaryAccountService {


    private final SalaryAccountRepository salaryAccountRepository;

    @Override
    public void createAccount(EmployeeEntity employeeEntity) {

        SalaryAccountEntity salaryAccount = SalaryAccountEntity.builder()
                .employee(employeeEntity)
                .balance(BigDecimal.ZERO)
                .build();

        salaryAccountRepository.save(salaryAccount);

    }
}
