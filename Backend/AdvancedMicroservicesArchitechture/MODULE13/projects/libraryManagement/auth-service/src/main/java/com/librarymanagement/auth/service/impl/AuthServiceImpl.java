package com.librarymanagement.auth.service.impl;

import com.librarymanagement.auth.dtos.RegisterRequest;
import com.librarymanagement.auth.dtos.RegisterResponse;
import com.librarymanagement.auth.entity.Role;
import com.librarymanagement.auth.entity.User;
import com.librarymanagement.auth.enums.RoleName;
import com.librarymanagement.auth.repository.RoleRepository;
import com.librarymanagement.auth.repository.UserRepository;
import com.librarymanagement.auth.service.AuthService;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;


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
}
