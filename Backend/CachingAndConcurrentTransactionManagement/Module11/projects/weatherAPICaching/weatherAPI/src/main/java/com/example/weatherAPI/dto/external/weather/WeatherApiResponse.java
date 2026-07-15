package com.example.weatherAPI.dto.external.weather;

import java.io.Serializable;

public record WeatherApiResponse(

        CurrentWeather current
)  {
}
