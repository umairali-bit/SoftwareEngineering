package com.example.cachingApp.dtos;

import java.time.LocalDate;

public record EmployeeDTO(
        Long id,
        String name,
        String email,
        Integer age,
        String role,
        LocalDate birthDate,
        boolean isActive
) {
}
