package com.example.AopApplication.aspects;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Before("execution(* com.example.AopApplication.services.impl.ShipmentServiceImpl.*(..))")
    public void beforeShipmentMethods(JoinPoint joinPoint) {
        log.info("Before Shipment: {}", joinPoint.getSignature());
    }
}
