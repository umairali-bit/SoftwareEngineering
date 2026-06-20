package com.example.AopApplication.aspects;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class GlobalErrorHandler {

    @AfterThrowing(pointcut = "execution(* com.example.AopApplication.services.*.*(..))", throwing = "ex")
    public void handleException(JoinPoint joinPoint, Exception ex) {
//        log.error(ex.getMessage(), ex);
        log.error("Exception occurred in {} : {} ",
                joinPoint.getSignature(),
                ex.getMessage());
    }
}
