package com.example.cachingApp.dtos;

import com.example.cachingApp.entities.EmployeeEntity;

import java.io.Serializable;
import java.math.BigDecimal;

public record SalaryAccountDTO(
        Long id,
        BigDecimal balance,
        EmployeeDTO employee
) implements Serializable {}
