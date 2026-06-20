package com.example.AopApplication.aspects;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

//    logging using @Before and @After

    @Pointcut("@annotation(com.example.AopApplication.aspects.MyLogging)")
    public void myLoggingPointcut() {
    }

    @Before("myLoggingPointcut()")
    public void logBefore() {
        log.info("Before myLoggingPointcut()");
    }

    @After("myLoggingPointcut()")
    public void logAfter() {
        log.info("After myLoggingPointcut()");
    }

//    execution time

    @Pointcut("execution(* com.example.AopApplication.services.impl.*.*(..)) ")
    public void allServiceMethodsPointcut() {}

    @Around("allServiceMethodsPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

        Long startTime = System.currentTimeMillis();


        try{
           return joinPoint.proceed();
       }
        finally {

            Long endTime = System.currentTimeMillis();

            Long diff = endTime - startTime;

            log.info("Execution time for {} : {} ",  joinPoint.getSignature().getName(), diff);

        }





    }





















}
