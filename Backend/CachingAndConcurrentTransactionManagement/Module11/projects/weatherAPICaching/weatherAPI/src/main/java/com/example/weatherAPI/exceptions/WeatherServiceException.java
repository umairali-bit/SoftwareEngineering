package com.example.weatherAPI.exceptions;

public class WeatherServiceException extends RuntimeException{
    public WeatherServiceException(String message){
        super(message);
    }
}
