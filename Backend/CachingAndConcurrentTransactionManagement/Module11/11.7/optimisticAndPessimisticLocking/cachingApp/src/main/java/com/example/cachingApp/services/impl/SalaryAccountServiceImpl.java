package com.example.cachingApp.services.impl;

import com.example.cachingApp.dtos.EmployeeDTO;
import com.example.cachingApp.dtos.SalaryAccountDTO;
import com.example.cachingApp.entities.EmployeeEntity;
import com.example.cachingApp.entities.SalaryAccountEntity;
import com.example.cachingApp.repositories.SalaryAccountRepository;
import com.example.cachingApp.services.SalaryAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional (propagation = Propagation.REQUIRED)
public class SalaryAccountServiceImpl implements SalaryAccountService {


    private final SalaryAccountRepository salaryAccountRepository;


    @Override
    @Transactional (propagation = Propagation.REQUIRED)
    public void createAccount(EmployeeEntity employee) {

        if ("Umair".equals(employee.getName())) throw new RuntimeException("Umair cannot create an account");

//        if(salaryAccountRepository.existsByEmployee(employee)) {
//            throw new RuntimeException("Salary account already exists");
//        }

        salaryAccountRepository.findByEmployee(employee)
                .ifPresent(account -> {
                    throw new IllegalStateException("Salary account already exists");
                });


            SalaryAccountEntity salaryAccount = SalaryAccountEntity.builder()
                    .employee(employee)
                    .balance(BigDecimal.ZERO)
                    .build();

            salaryAccountRepository.save(salaryAccount);


    }

    @Override
//    @Transactional (isolation = Isolation.SERIALIZABLE)
    public SalaryAccountDTO implementBalance(Long accountId) throws InterruptedException {


        SalaryAccountEntity salaryAccount = salaryAccountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Salary account with id " + accountId + " not found"));

        EmployeeEntity employee = salaryAccount.getEmployee();

//        BigDecimal balance = salaryAccount.getBalance();
//        BigDecimal newBalance = balance.add(BigDecimal.valueOf(1L));
//        salaryAccount.setBalance(newBalance);

        salaryAccount.setBalance(salaryAccount.getBalance().add(BigDecimal.ONE));

       Thread.sleep(1000); //  adding delay for testing purposes




        salaryAccount = salaryAccountRepository.save(salaryAccount);

        return new SalaryAccountDTO(
                salaryAccount.getId(),
                salaryAccount.getBalance(),
                new EmployeeDTO(
                        employee.getId(),
                        employee.getName(),
                        employee.getEmail(),
                        employee.getAge(),
                        employee.getRole(),
                        employee.getBirthDate(),
                        employee.isActive(),
                        employee.getSalary()
                )
        );
    }
}
