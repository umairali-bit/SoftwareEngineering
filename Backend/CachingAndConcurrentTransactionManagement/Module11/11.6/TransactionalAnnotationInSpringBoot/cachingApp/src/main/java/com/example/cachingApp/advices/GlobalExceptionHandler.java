package com.example.cachingApp.advices;

import com.example.cachingApp.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceNotFoundException(
            ResourceNotFoundException ex) {

        ApiErrorResponse response =
                new ApiErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        ex.getMessage()
                );

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiErrorResponse> handleException(
//            Exception ex) {
//
//        ApiErrorResponse response =
//                new ApiErrorResponse(
//                        LocalDateTime.now(),
//                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                        ex.getMessage()
//                );
//
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(response);
//    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(
            Exception ex) {

        ex.printStackTrace();

        ApiErrorResponse response =
                new ApiErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        ex.getClass().getName() + " : " + ex.getMessage()
                );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}
