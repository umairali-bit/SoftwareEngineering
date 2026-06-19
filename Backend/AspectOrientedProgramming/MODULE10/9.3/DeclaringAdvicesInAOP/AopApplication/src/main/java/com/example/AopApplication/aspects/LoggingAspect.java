package com.example.AopApplication.aspects;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

//@Aspect
@Component
@Slf4j
public class LoggingAspect {


// any method in ShipmentServiceImpl class

    @Before("execution(* com.example.AopApplication.services.impl.ShipmentServiceImpl.*(..))")
    public void beforeShipmentMethods(JoinPoint joinPoint) {
        log.info("Before Shipment, kind: {}", joinPoint.getKind());
        log.info("Before Shipment, signature: {}", joinPoint.getSignature());
    }

//    within services.impl class

    @Before("within(com.example.AopApplication.services.impl.*)")
    public void beforeWithinMethods() {
        log.info("Service Impl Calls");
    }

//    Before Transactional annotation
   @Before("@annotation(org.springframework.transaction.annotation.Transactional)")
   public void beforeTransactionalAnnotationCalls() {
        log.info("Before Transactional Annotation calls");
    }

//    Before custom annotations
    @Before("@annotation(com.example.AopApplication.aspects.MyLogging)")
    public void beforeCustomAnnotationCalls() {
        log.info("Before My Logging Annotation calls");
    }

//    Custom pointcuts
    @Pointcut("@annotation(com.example.AopApplication.aspects.MyLogging) && within(com.example.AopApplication.services.impl.*)")
    public void annotationPointcut() {}

    @After("annotationPointcut()")
    public void afterAnnotationPointcut() {
        log.info("After Annotation calls");
    }

    @Before("annotationPointcut()")
    public void beforeAnnotationPointcut() {
        log.info("Before Annotation Pointcut");
    }


}
