package com.example.moduleRecap;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "dev.env", havingValue = "syrup")
public class StrawberrySyrup implements Syrup{


    @Override
    public String getSyrupType() {
        return "Strawberry Syrup";
    }
}
