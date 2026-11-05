package com.librarymanagement.auth.dtos.login;

public record LoginResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String message
) {


}
