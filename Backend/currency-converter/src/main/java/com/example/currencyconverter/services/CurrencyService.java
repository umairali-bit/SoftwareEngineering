package com.example.currencyconverter.services;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class CurrencyService {

    @Value("${freecurrency.api.base-url}")
    private String baseUrl;

    @Value("${freecurrency.api.key}")
    private String apiKey;

    private final RestClient currencyRestClient;

    public CurrencyService(RestClient currencyRestClient) {
        this.currencyRestClient = currencyRestClient;
    }


    @PostConstruct
    public void testConfig() {
        System.out.println("Base URL: " + baseUrl);
        System.out.println("API Key Loaded: " + apiKey.substring(0,6) + "*****");
    }
}