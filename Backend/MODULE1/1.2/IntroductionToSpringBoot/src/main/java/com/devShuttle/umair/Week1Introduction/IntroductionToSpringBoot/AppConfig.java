package com.devShuttle.umair.Week1Introduction.IntroductionToSpringBoot;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/*
In Spring Boot (and Spring in general), a configuration class is a special class annotated with
@Configuration that is used to define beans manually and provide configuration settings to the Spring container.
 */
@Configuration
public class AppConfig {

    @Bean
    @Scope("prototype")
    Apple getApple() {
        return new Apple();
    }
}
