package com.example.moduleRecap;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;


@Service
@Scope(SCOPE_PROTOTYPE)
public class CakeBaker {

    private final List<Syrup> syrups;
    private final List<Frosting> frostings;
    private final String env;

    public CakeBaker(List<Syrup> syrups, List<Frosting> frostings, @Value("${dev.env}") String env) {
        this.syrups = syrups;
        this.frostings = frostings;
        this.env = env;
    }

    public String getToppings() {
        if ("syrup".equalsIgnoreCase(env)) {
            return syrups.stream()
                    .map(Syrup::getSyrupType)
                    .collect(Collectors.joining(", "));
        } else if ("frosting".equalsIgnoreCase(env)) {
            return frostings.stream()
                    .map(Frosting::getFrostingType)
                    .collect(Collectors.joining(", "));
        } else {
            return "Unknown env value: " + env;
        }
    }
}
