package com.example.AopApplication.aspects;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ValidationAspect {

    @Pointcut("execution(* com.example.AopApplication.services.impl.*.*(..))")
    public void allServiceMethodsPointcut() {
    }

    @Around("allServiceMethodsPointcut()")
    public Object validate(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object[] args = proceedingJoinPoint.getArgs();

        Long orderId = (Long)args[0];

        if(orderId > 0) return proceedingJoinPoint.proceed();

        return "Cannot call with a negative order Id";
    }
}
