package com.devShuttle.umair.Week1Introduction.IntroductionToSpringBoot;

import jakarta.annotation.PostConstruct;
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
}
