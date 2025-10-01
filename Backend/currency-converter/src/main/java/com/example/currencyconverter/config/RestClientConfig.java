package com.example.currencyconverter.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;


@Slf4j
@Configuration
public class RestClientConfig {

    @Value("${freecurrency.api.base-url}")
    private String BASE_URL;

    @Value("${FREECURRENCY_API_KEY}")
    private String API_KEY;

    @Bean
    public RestClient currencyRestClient(RestClient.Builder builder) {
        return builder
                .baseUrl(BASE_URL)
                .defaultHeader("apikey", API_KEY)
                .build();
    }


}
