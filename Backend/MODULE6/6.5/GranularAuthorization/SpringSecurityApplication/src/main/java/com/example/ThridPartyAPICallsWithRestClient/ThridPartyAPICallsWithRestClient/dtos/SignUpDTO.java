package com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.dtos;


import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.entities.enums.Role;
import lombok.Data;
import jakarta.validation.constraints.*;
import com.example.ThridPartyAPICallsWithRestClient
        .ThridPartyAPICallsWithRestClient.entities.enums.Permissions;
import java.util.Set;

@Data
public class SignUpDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    private Set<Role> roles;

    private Set<Permissions> permissions;
}
