package com.devShuttle.umair.Week1Introduction.IntroductionToSpringBoot;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

//@Component //@Component in Spring is a stereotype annotation that marks a class as a Spring-managed bean
public class Apple {


    void eatApple() {
        System.out.println("I am eating the apple");
    }

    @PostConstruct
    void callThisBeforeAppleIsUsed() {
        System.out.println("Creating the apple before its use");
    }

    @PreDestroy //works after the bean is destroyed, after springboot stop running
    void callThisBeforeDestroy() {
        System.out.println("Destroying the apple bean");

    }
}
