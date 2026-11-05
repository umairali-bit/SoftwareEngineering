package com.librarymanagement.auth.service;


import com.librarymanagement.auth.dtos.login.LoginRequest;
import com.librarymanagement.auth.dtos.login.LoginResponse;
import com.librarymanagement.auth.dtos.register.RegisterRequest;
import com.librarymanagement.auth.dtos.register.RegisterResponse;

public interface AuthService {

    RegisterResponse register (RegisterRequest request);

    LoginResponse login(LoginRequest request);
}
