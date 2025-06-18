package com.example.addingDepartment.AddingDepartment.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PrimeNumberInputValidator implements ConstraintValidator<PrimeNumberInput, Integer>{


    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        if (integer == null || integer < 2) return false;
        for (int i = 2; i<=Math.sqrt(integer); i++) {
            if (integer % i == 0) return false;
        }
        return true;

    }
}
