package com.umair.librarymanagement.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;

public class BookTitleValidator implements ConstraintValidator<ValidateBookTitle, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.length() >= 3;
    }
}
