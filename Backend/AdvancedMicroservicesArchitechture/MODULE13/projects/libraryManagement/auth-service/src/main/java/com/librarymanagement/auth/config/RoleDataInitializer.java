package com.librarymanagement.auth.config;

import com.librarymanagement.auth.entity.Role;
import com.librarymanagement.auth.enums.RoleName;
import com.librarymanagement.auth.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;



@Configuration
@RequiredArgsConstructor
public class RoleDataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;


    @Override
    public void run(String... args) throws Exception {

        for (RoleName roleName : RoleName.values()) {

            if(roleRepository.findByRoleName(roleName).isEmpty()) {
                roleRepository.save(new Role(roleName));
            }
        }

    }
}
