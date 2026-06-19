package com.example.AopApplication.aspects;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogginAscpectV2 {

    @Pointcut("execution(* com.example.AopApplication.services.impl.*.*(..))")
    public void allServiceMethodsPointcut() {

    }

    @Before("allServiceMethodsPointcut()")
    public void beforeAllServiceMethods(JoinPoint joinPoint) {
        log.info("Before all service methods called, {}", joinPoint.getSignature());
    }

    @After("allServiceMethodsPointcut()")
    public void afterAllServiceMethods(JoinPoint joinPoint) {
        log.info("After all service methods called, {}", joinPoint.getSignature());
    }

    @AfterReturning("allServiceMethodsPointcut()")
    public void afterReturningAllServiceMethods(JoinPoint joinPoint) {
        log.info("After returning all service methods called, {}", joinPoint.getSignature());
    }

//    returning a value
    @AfterReturning(value = "allServiceMethodsPointcut()", returning = "returnedObj")
    public void afterReturningAllServiceMethods(JoinPoint joinPoint, Object returnedObj) {
        log.info("After returning all service methods called, {}", joinPoint.getSignature());
        log.info("returning the object: {} ", returnedObj);
    }

//   after throwing an exception
    @AfterThrowing(value = "allServiceMethodsPointcut()")
    public void afterThrowingAllServiceMethods(JoinPoint joinPoint) {

        log.info("After throwing advice method call, {} " , joinPoint.getSignature());
}

//   execution time
    @Around(value = "allServiceMethodsPointcut()")
    public Object logExecutionTIme(ProceedingJoinPoint  joinPoint) throws Throwable {

        Long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        Long endTime = System.currentTimeMillis();
        Long diff = endTime - startTime;
        log.info("execution time for {} is : {} ms",joinPoint.getSignature(), diff);
        return result;
    }


}
