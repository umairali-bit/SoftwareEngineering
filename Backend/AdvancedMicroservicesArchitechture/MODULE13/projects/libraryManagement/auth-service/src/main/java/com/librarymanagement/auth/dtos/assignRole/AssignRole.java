package com.librarymanagement.auth.dtos.assignRole;

import com.librarymanagement.auth.enums.RoleName;
import jakarta.validation.constraints.NotNull;

public record AssignRole(

        @NotNull
        RoleName roleName
) {
}
