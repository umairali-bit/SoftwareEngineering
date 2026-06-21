package com.example.AopApplication.aspects;


import com.example.AopApplication.aspects.annotations.ValidateEmployee;
import com.example.AopApplication.entity.Employee;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


import java.util.Set;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class ValidationAspect {

    private final Validator validator;

    @Around("@annotation(com.example.AopApplication.aspects.annotations.ValidateEmployee)")
    public Object validate(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();
        Employee employee = (Employee) args[0];


        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);

        if (!violations.isEmpty()) {

            String errorMessage = violations.stream()
                    .map(c -> c.getMessage())
                    .collect(Collectors.joining(", "));
            throw new IllegalArgumentException(errorMessage);
        }

        return joinPoint.proceed();
    }
}
