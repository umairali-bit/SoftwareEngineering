package com.example.weatherAPI.service.impl;

import com.example.weatherAPI.client.GeocodingClient;
import com.example.weatherAPI.client.WeatherClient;
import com.example.weatherAPI.dto.WeatherResponse;
import com.example.weatherAPI.dto.external.geocoding.LocationResult;
import com.example.weatherAPI.dto.external.weather.WeatherApiResponse;
import com.example.weatherAPI.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;


@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final GeocodingClient geocodingClient;
    private final WeatherClient weatherClient;


    @Override
    public WeatherResponse getCurrentWeather(String city) {

        LocationResult location = geocodingClient.findLocation(city);
        WeatherApiResponse weather = weatherClient.getCurrentWeather(
                location.latitude(),
                location.longitude()
        );

        return new WeatherResponse(
                location.name(),
                location.country(),
                weather.current().temperature_2m(),
                weather.current().wind_speed_10m(),
                weather.current().weather_code(),
                weather.current().time(),
                false
        );
    }
}
