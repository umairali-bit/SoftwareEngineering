package com.example.weatherAPI.service;

import com.example.weatherAPI.dto.WeatherResponse;
import com.example.weatherAPI.dto.external.weather.WeatherApiResponse;

public interface WeatherService {

    WeatherResponse getCurrentWeather(String city);
}
