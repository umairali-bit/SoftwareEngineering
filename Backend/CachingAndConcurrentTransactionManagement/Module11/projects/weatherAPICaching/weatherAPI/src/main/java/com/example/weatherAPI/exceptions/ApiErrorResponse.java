package com.example.weatherAPI.exceptions;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ApiErrorResponse(

        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path


) {
}
