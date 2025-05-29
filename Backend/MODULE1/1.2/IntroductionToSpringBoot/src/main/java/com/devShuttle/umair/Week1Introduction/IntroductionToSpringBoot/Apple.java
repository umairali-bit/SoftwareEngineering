package com.devShuttle.umair.Week1Introduction.IntroductionToSpringBoot;

import org.springframework.stereotype.Component;

@Component //@Component in Spring is a stereotype annotation that marks a class as a Spring-managed bean
public class Apple {


    void eatApple() {
        System.out.println("I am eating the apple");
    }
}
