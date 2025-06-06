package com.example.moduleRecap;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "dev.env", havingValue = "frosting")
public class ChocolateFrosting implements Frosting{


    public String getFrostingType() {
        return "Chocolate Frosting";
    }
}
