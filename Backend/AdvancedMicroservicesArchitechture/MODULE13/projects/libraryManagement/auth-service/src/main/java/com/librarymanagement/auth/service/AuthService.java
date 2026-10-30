package com.librarymanagement.auth.service;


import com.librarymanagement.auth.dtos.RegisterRequest;
import com.librarymanagement.auth.dtos.RegisterResponse;

public interface AuthService {

    RegisterResponse register (RegisterRequest request);
}
