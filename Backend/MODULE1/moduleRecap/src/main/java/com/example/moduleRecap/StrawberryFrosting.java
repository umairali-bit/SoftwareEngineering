package com.example.moduleRecap;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "dev.env", havingValue = "frosting")
public class StrawberryFrosting implements Frosting{

    public String getFrostingType() {
        return "Strawberry Frosting";
    }
}
