package com.librarymanagement.auth.config;

import com.librarymanagement.auth.entity.Role;
import com.librarymanagement.auth.entity.User;
import com.librarymanagement.auth.enums.RoleName;
import com.librarymanagement.auth.repository.RoleRepository;
import com.librarymanagement.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;


@Configuration
@RequiredArgsConstructor
public class RoleDataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Value("${app.bootstrap.admin-enabled:false}")
    private boolean adminBootstrapEnabled;

    @Value("${app.bootstrap.admin-email:}")
    private String adminEmail;

    @Override
    @Transactional
    public void run(String... args) {

        // Step 1: Initialize all roles
        for (RoleName roleName : RoleName.values()) {

            if (roleRepository.findByRoleName(roleName).isEmpty()) {
                roleRepository.save(new Role(roleName));
            }
        }

        // Step 2: Bootstrap admin only when explicitly enabled
        if (!adminBootstrapEnabled) {
            return;
        }

        if (adminEmail.isBlank()) {
            throw new IllegalStateException(
                    "Bootstrap admin email is required"
            );
        }

        // Step 3: Find existing user
        User admin = userRepository.findByEmail(adminEmail)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Bootstrap admin user not found"
                        )
                );

        // Step 4: Retrieve ADMIN role
        Role adminRole = roleRepository
                .findByRoleName(RoleName.ADMIN)
                .orElseThrow();

        // Step 5: Check whether user already has ADMIN
        boolean alreadyAdmin = admin.getRoles()
                .stream()
                .anyMatch(role ->
                        role.getRoleName() == RoleName.ADMIN
                );

        // Step 6: Assign ADMIN if missing
        if (!alreadyAdmin) {
            admin.getRoles().add(adminRole);
            userRepository.save(admin);
        }
    }
}