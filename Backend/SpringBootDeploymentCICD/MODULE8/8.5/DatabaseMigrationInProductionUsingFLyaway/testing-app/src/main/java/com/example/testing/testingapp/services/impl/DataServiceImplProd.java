package com.example.testing.testingapp.services.impl;

import com.example.testing.testingapp.services.DataService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("prod")
public class DataServiceImplProd implements DataService {
    @Override
    public String getData() {
        return"Prod Data";
    }
}
