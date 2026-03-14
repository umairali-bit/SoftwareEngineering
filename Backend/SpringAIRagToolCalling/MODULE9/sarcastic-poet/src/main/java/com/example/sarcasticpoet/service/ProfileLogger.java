package com.example.sarcasticpoet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class ProfileLogger {

    private final Environment environment;

    @EventListener(ApplicationReadyEvent.class)
    public void logProfiles() {

        System.out.println("Active profiles: " +
                Arrays.toString(environment.getActiveProfiles()));

        System.out.println("Default profiles: " +
                Arrays.toString(environment.getDefaultProfiles()));
    }


}
