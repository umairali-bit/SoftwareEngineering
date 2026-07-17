package com.example.weatherAPI.service;

import com.example.weatherAPI.client.GeocodingClient;
import com.example.weatherAPI.client.WeatherClient;
import com.example.weatherAPI.dto.WeatherResponse;
import com.example.weatherAPI.dto.external.geocoding.LocationResult;
import com.example.weatherAPI.dto.external.weather.CurrentWeather;
import com.example.weatherAPI.dto.external.weather.WeatherApiResponse;
import com.example.weatherAPI.service.impl.WeatherServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeatherServiceImplTest {

    @Mock
    private GeocodingClient geocodingClient;

    @Mock
    private WeatherClient weatherClient;

    @Mock
    private CacheManager cacheManager;

    @Mock
    private Cache cache;

    @InjectMocks
    private WeatherServiceImpl weatherService;

    @Test
    void shouldReturnCachedWeatherWhenCacheHit() {

        WeatherResponse cachedResponse = new WeatherResponse(
                "Lahore",
                "Pakistan",
                31.5,
                8.0,
                3,
                LocalDateTime.now(),
                false
        );

        when(cacheManager.getCache("weatherCache"))
                .thenReturn(cache);

        when(cache.get("lahore", WeatherResponse.class))
                .thenReturn(cachedResponse);

        WeatherResponse response =
                weatherService.getCurrentWeather("Lahore");

        assertNotNull(response);
        assertEquals("Lahore", response.city());
        assertEquals("Pakistan", response.country());
        assertEquals(31.5, response.temperature());
        assertEquals(8.0, response.windSpeed());
        assertEquals(3, response.weatherCode());
        assertTrue(response.cached());

        verify(cacheManager).getCache("weatherCache");
        verify(cache).get("lahore", WeatherResponse.class);

        verify(geocodingClient, never()).findLocation(anyString());
        verify(weatherClient, never()).getCurrentWeather(anyDouble(), anyDouble());
    }

    @Test
    void shouldFetchWeatherWhenCacheMiss() {

        LocationResult location = new LocationResult(
                "Lahore",
                "Pakistan",
                31.5497,
                74.3436
        );

        CurrentWeather currentWeather = new CurrentWeather(
                LocalDateTime.now(),
                29.4,
                10.2,
                2
        );

        WeatherApiResponse weatherApiResponse =
                new WeatherApiResponse(currentWeather);

        when(cacheManager.getCache("weatherCache"))
                .thenReturn(cache);

        when(cache.get("lahore", WeatherResponse.class))
                .thenReturn(null);

        when(geocodingClient.findLocation("Lahore"))
                .thenReturn(location);

        when(weatherClient.getCurrentWeather(31.5497, 74.3436))
                .thenReturn(weatherApiResponse);

        WeatherResponse response =
                weatherService.getCurrentWeather("Lahore");

        assertNotNull(response);
        assertEquals("Lahore", response.city());
        assertEquals("Pakistan", response.country());
        assertEquals(29.4, response.temperature());
        assertEquals(10.2, response.windSpeed());
        assertEquals(2, response.weatherCode());
        assertFalse(response.cached());

        verify(cacheManager).getCache("weatherCache");
        verify(cache).get("lahore", WeatherResponse.class);

        verify(geocodingClient).findLocation("Lahore");
        verify(weatherClient).getCurrentWeather(31.5497, 74.3436);

        verify(cache).put("lahore", response);
    }
}