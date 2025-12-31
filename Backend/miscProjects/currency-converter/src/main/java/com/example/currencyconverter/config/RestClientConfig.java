package com.example.currencyconverter.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;


@Configuration
public class RestClientConfig {

    @Bean
    public RestClient currencyRestClient(
            @Value("${freecurrency.api.base-url}") String baseUrl,
            @Value("${freecurrency.api.key}") String apiKey) {

        return RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("apikey", apiKey) // ✅ FreeCurrencyAPI expects this header
                .build();
    }
}
