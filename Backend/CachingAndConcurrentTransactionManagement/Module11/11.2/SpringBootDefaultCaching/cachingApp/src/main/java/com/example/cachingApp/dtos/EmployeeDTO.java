package com.example.cachingApp.dtos;

import java.time.LocalDate;

public record EmployeeDTO(
        int id,
        String name,
        String email,
        Integer age,
        String role,
        LocalDate birthday,
        boolean isActive
) {
}
