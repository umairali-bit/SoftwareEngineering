package com.librarymanagement.auth.controller;

import com.librarymanagement.auth.dtos.login.LoginRequest;
import com.librarymanagement.auth.dtos.login.LoginResponse;
import com.librarymanagement.auth.dtos.register.RegisterRequest;
import com.librarymanagement.auth.dtos.register.RegisterResponse;
import com.librarymanagement.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final SecurityContextRepository securityContextRepository;
    private final SessionAuthenticationStrategy sessionAuthenticationStrategy;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {

        RegisterResponse registerResponse = authService.register(registerRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(registerResponse);
    }

    @RequestMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest loginRequest,
            HttpServletRequest request,
            HttpServletResponse response) {
        LoginResponse loginResponse = authService.login(loginRequest);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        sessionAuthenticationStrategy.onAuthentication(
                authentication,
                request,
                response
        );

        securityContextRepository.saveContext(
                SecurityContextHolder.getContext(),
                request,
                response
        );

        HttpSession session = request.getSession(false);

//        System.out.println("Authenticated user: " + authentication.getName());
//
//        System.out.println("Session exists: " + (session != null));
//
//        System.out.println("SecurityContext saved: " +
//                (session != null &&
//                        session.getAttribute("SPRING_SECURITY_CONTEXT") != null));



        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loginResponse);
    }


    @GetMapping("/csrf")
    public CsrfToken csrf(CsrfToken csrfToken) {
        return csrfToken;
    }

    @GetMapping("/me")
    public ResponseEntity<String> me(Authentication authentication) {
        String email = authentication.getName();

        return ResponseEntity.ok(email);
    }
}
