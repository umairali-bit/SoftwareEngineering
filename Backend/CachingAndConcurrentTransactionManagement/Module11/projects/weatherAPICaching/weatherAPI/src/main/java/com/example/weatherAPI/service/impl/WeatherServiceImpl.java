package com.example.weatherAPI.service.impl;

import com.example.weatherAPI.client.GeocodingClient;
import com.example.weatherAPI.client.WeatherClient;
import com.example.weatherAPI.dto.WeatherResponse;
import com.example.weatherAPI.dto.external.geocoding.LocationResult;
import com.example.weatherAPI.dto.external.weather.WeatherApiResponse;
import com.example.weatherAPI.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor

public class WeatherServiceImpl implements WeatherService {

    private static final String CACHE_NAME = "weatherCache";

    private final GeocodingClient geocodingClient;
    private final WeatherClient weatherClient;
    private final CacheManager cacheManager;

    @Override
    public WeatherResponse getCurrentWeather(String city) {

        long startTime = System.currentTimeMillis();

        String cacheKey = city.toLowerCase();

        Cache cache = cacheManager.getCache(CACHE_NAME);

        log.debug("Looking up weather cache for city={}, key={}", city, cacheKey);


        if (cache != null) {

            WeatherResponse cachedResponse = cache.get(cacheKey, WeatherResponse.class);


            if (cachedResponse != null) {
                log.info("Cache hit for city={}", city);

                WeatherResponse response = new WeatherResponse(
                        cachedResponse.city(),
                        cachedResponse.country(),
                        cachedResponse.temperature(),
                        cachedResponse.windSpeed(),
                        cachedResponse.weatherCode(),
                        cachedResponse.date(),
                        true
                );
                log.info(
                        "Weather request completed for city={} in {} ms",
                        city,
                        System.currentTimeMillis() - startTime
                );


                return response;


            }

        }

        log.info("Cache miss for city={}", city);
        log.debug("Fetching fresh weather data for city={}", city);

        // Cache Miss -> Call external APIs

        LocationResult location = geocodingClient.findLocation(city);
        WeatherApiResponse weather = weatherClient.getCurrentWeather(
                location.latitude(),
                location.longitude()
        );

        WeatherResponse weatherResponse = new WeatherResponse(
                location.name(),
                location.country(),
                weather.current().temperature_2m(),
                weather.current().wind_speed_10m(),
                weather.current().weather_code(),
                weather.current().time(),
                false
        );


        if (cache != null) {
            cache.put(cacheKey, weatherResponse);
            log.debug("Stored weather response in cache for city={}", city);
        }

        log.info(
                "Weather request completed for city={} in {} ms",
                city,
                System.currentTimeMillis() - startTime
        );

        return weatherResponse;
    }
}
