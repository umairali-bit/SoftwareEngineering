package com.example.weatherAPI.config;



import com.example.weatherAPI.exceptions.WeatherServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;


@Configuration
public class RestClientConfig {

    @Value("${weather.geocoding.base-url}")
    private String geocodingBaseUrl;

    @Value("${weather.api.base-url}")
    private String weatherBaseUrl;

    @Bean(name = "geocodingRestClient")
    public RestClient geocodingRestClient() {

        return RestClient.builder()
                .baseUrl(geocodingBaseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultStatusHandler(
                        HttpStatusCode::is5xxServerError,
                        ((request, response) ->
                        {
                            throw new WeatherServiceException("Geocoding API is unavailable");
                        })
                ).build();
    }

    @Bean (name = "weatherRestClient")
    public RestClient weatherRestClient() {

        return RestClient.builder()
                .baseUrl(weatherBaseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultStatusHandler(
                        HttpStatusCode::is5xxServerError,
                        ((request, response) ->{
                            throw new WeatherServiceException("Weather API is unavailable");
                        })
                )

                .build();
    }


}
