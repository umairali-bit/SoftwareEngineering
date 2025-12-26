package com.example.addingDepartment.AddingDepartment.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PrimeNumberInputValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PrimeNumberInput {
    String message() default "The number must be a prime number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

