package com.example.weatherAPI.dto;

import java.time.LocalDateTime;

public record weatherResponse (

        String city,
        String country,
        double temperature,
        double windSpeed,
        int weatherCode,
        LocalDateTime date,
        boolean cached

) {
}
