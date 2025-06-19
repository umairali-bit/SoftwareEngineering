package com.example.addingDepartment.AddingDepartment.annotations;

import jakarta.validation.Payload;

public @interface ValidPassword {
    String message() default "Password must be at least 10 characters long, contain one uppercase, one lowercase, and one special character";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
