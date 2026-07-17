package com.example.weatherAPI.client;

import com.example.weatherAPI.dto.external.weather.WeatherApiResponse;
import com.example.weatherAPI.exceptions.WeatherServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
public class WeatherClient {

    private final RestClient restClient;

    public WeatherClient(
            @Qualifier("weatherRestClient")
            RestClient restClient) {

        this.restClient = restClient;
    }

    public WeatherApiResponse getCurrentWeather(
            double latitude,
            double longitude) {

        log.info(
                "Calling weather API for latitude={}, longitude={}",
                latitude,
                longitude
        );

        WeatherApiResponse response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/forecast")
                        .queryParam("latitude", latitude)
                        .queryParam("longitude", longitude)
                        .queryParam("current",
                                "temperature_2m,weather_code,wind_speed_10m")
                        .build())
                .retrieve()
                .body(WeatherApiResponse.class);

        log.debug(
                "Weather API call completed for latitude={}, longitude={}",
                latitude,
                longitude
        );

        if (response == null || response.current() == null) {
            throw new WeatherServiceException(
                    "Unable to retrieve weather information.");
        }

        return response;
    }
}
