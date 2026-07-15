package com.example.weatherAPI.dto.external.weather;

import com.example.weatherAPI.dto.WeatherResponse;

import java.io.Serializable;
import java.time.LocalDateTime;

public record CurrentWeather(

        LocalDateTime time,
        double temperature_2m,
        double wind_speed_10m,
        int weather_code
) {
}
