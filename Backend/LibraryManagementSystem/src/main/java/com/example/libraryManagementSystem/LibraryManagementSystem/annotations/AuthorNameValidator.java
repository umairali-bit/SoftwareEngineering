package com.example.libraryManagementSystem.LibraryManagementSystem.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AuthorNameValidator implements ConstraintValidator<ValidateAuthorName, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;

        // Business rule: at least 3 chars, letters & spaces only
        return value.length() >= 3 && value.matches("^[A-Za-z .'-]+$");


    }
}
