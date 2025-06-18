package com.example.addingDepartment.AddingDepartment.annotations;


import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface PrimeNumberInput {

    String message() default "The number must be a prime number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
