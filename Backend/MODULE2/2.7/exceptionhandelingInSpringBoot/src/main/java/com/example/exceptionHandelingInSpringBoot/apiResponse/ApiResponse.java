package com.example.exceptionHandelingInSpringBoot.apiResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ApiResponse<T> {

    private LocalDateTime timeStamp;
    private T data;
    private ApiError error;

    public ApiResponse() {
        this.timeStamp = LocalDateTime.now();
    }

    public ApiResponse(T data) {
        this.data = data;
    }
}
