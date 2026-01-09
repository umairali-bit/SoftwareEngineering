package com.example.libraryManagementSystem.LibraryManagementSystem.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BookTitleValidator.class)
public @interface ValidateBookTitle {
    String message() default "Invalid book title";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
