package com.example.weatherAPI.dto;

import java.time.LocalDateTime;

public record WeatherResponse(

        String city,
        String country,
        double temperature,
        double windSpeed,
        int weatherCode,
        LocalDateTime date,
        boolean cached

) {
}
