package com.example.weatherAPI.exceptions;


import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(
            Exception ex,
            HttpServletRequest request) {

//        ex.printStackTrace();
        log.error(
                "Unexpected exception. Path={}, Message={}",
                request.getRequestURI(),
                ex.getMessage(),
                ex
        );

        ApiErrorResponse errorResponse = new ApiErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }


    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleCityNotFoundException(CityNotFoundException ex,
                                                                        HttpServletRequest request){

        log.warn(
                "City not found. Path={}, Message={}",
                request.getRequestURI(),
                ex.getMessage()
        );

        ApiErrorResponse errorResponse = new ApiErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );

//        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse);

    }

    @ExceptionHandler(WeatherServiceException.class)
    public ResponseEntity<ApiErrorResponse> handleWeatherServiceException(WeatherServiceException ex,
                                                                          HttpServletRequest request) {

        log.error(
                "Weather service failed. Path={}, Message={}",
                request.getRequestURI(),
                ex.getMessage(),
                ex
        );
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }





}
