package com.example.weatherAPI.dto.external.weather;

import java.time.LocalDateTime;

public record CurrentWeather(

        LocalDateTime time,
        double temperature_2m,
        double wind_speed_10m,
        int weather_code
) {
}
