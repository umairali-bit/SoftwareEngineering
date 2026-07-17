package com.example.weatherAPI.controller;


import com.example.weatherAPI.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/current")
    public ResponseEntity<?> getCurrentWeather(@RequestParam String city) {

        log.info("Received current weather request for city={}", city);

        return ResponseEntity.ok(weatherService.getCurrentWeather(city));

    }
}
