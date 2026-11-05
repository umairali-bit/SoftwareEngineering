package com.librarymanagement.auth.dtos.register;

public record RegisterResponse(

        Long id,
        String firstName,
        String lastName,
        String email,
        String role
) {
}
