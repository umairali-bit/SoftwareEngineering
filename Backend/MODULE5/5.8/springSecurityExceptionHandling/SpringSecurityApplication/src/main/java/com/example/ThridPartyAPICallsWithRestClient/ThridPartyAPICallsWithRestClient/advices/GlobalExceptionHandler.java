package com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.advices;

import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.exceptions.ResourceNotFoundException;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex) {

        ApiError apiError = new ApiError(ex.getLocalizedMessage(), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler(AuthenticationException.class)
//    public ResponseEntity<ApiError> handleAuthentication(AuthenticationException ex) {
//        ApiError apiError = new ApiError(ex.getLocalizedMessage(), HttpStatus.UNAUTHORIZED);
//    }
}
