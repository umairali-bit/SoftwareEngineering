package com.example.cachingApp.dtos;

import com.example.cachingApp.entities.EmployeeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.math.BigDecimal;

public record SalaryAccountDTO(
        Long id,
        BigDecimal balance,
        @JsonIgnore
        EmployeeDTO employee
) implements Serializable {}
