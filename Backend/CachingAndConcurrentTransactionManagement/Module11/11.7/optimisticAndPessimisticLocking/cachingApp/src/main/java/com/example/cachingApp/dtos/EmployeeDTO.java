package com.example.cachingApp.dtos;

import lombok.Data;

import java.time.LocalDate;
import java.io.Serializable;

public record EmployeeDTO(
        Long id,
        String name,
        String email,
        Integer age,
        String role,
        LocalDate birthDate,
        boolean isActive,
        Long salary
) implements Serializable {
}
