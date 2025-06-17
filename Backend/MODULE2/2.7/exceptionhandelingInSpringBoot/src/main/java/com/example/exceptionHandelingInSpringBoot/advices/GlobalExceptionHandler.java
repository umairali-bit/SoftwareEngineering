package com.example.exceptionHandelingInSpringBoot.advices;


import com.example.exceptionHandelingInSpringBoot.exceptions.ResourceNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<APIError> exceptionResourceNotFound(ResourceNotFound exception) {
        APIError apierror = APIError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(apierror, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIError> handleInternalServerError(Exception exception) {
        APIError apierror = APIError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(apierror, HttpStatus.INTERNAL_SERVER_ERROR);

    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<APIError> inputValidationErrors (MethodArgumentNotValidException exception) {
        List<String> errors = exception
                .getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());


        APIError apierror = APIError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Input validation failed")
                .subErrors(errors)
                .build();
        return new ResponseEntity<>(apierror, HttpStatus.NOT_FOUND);



    }

}
