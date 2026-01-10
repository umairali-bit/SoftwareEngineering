package com.umair.librarymanagement.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AuthorNameValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateAuthorName {

    String message() default "Invalid Author Name: must be at least 3 characters and only contain letter";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
