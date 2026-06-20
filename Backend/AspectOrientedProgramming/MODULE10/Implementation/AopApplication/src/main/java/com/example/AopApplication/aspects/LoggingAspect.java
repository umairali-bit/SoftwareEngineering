package com.example.AopApplication.aspects;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

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
}
