package com.librarymanagement.auth.service.impl;

import com.librarymanagement.auth.dtos.login.LoginRequest;
import com.librarymanagement.auth.dtos.login.LoginResponse;
import com.librarymanagement.auth.dtos.register.RegisterRequest;
import com.librarymanagement.auth.dtos.register.RegisterResponse;
import com.librarymanagement.auth.entity.Role;
import com.librarymanagement.auth.entity.User;
import com.librarymanagement.auth.enums.RoleName;
import com.librarymanagement.auth.repository.RoleRepository;
import com.librarymanagement.auth.repository.UserRepository;
import com.librarymanagement.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;


//  Helper method
    public void validateUser(RegisterRequest request) {

        if(userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already exists");
        }

    }


    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest request) {

        validateUser(request);

        Role userRole = roleRepository
                .findByRoleName(RoleName.MEMBER)
                .orElseThrow(()->new RuntimeException("Role not found"));

        User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .roles(userRole)
                .build();

        User savedUser = userRepository.save(user);


        return new RegisterResponse(
                savedUser.getId(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getEmail(),
                savedUser.getRoles().getRoleName().name()
        );
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(()->new RuntimeException("User not found"));


        return new LoginResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                "Login successful"
        );
    }
}
