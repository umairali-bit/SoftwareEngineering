package com.example.exceptionHandelingInSpringBoot.advices;


import com.example.exceptionHandelingInSpringBoot.exceptions.ResourceNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<APIError> exceptionResourceNotFound(ResourceNotFound exception) {
      APIError apierror = APIError.builder()
              .status(HttpStatus.NOT_FOUND)
              .message(exception.getMessage())
              .build();
      return new ResponseEntity<>(apierror,HttpStatus.NOT_FOUND);
    }



}
