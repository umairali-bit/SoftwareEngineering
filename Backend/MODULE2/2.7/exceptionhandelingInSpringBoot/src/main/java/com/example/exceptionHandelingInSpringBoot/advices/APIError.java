package com.example.exceptionHandelingInSpringBoot.advices;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;


@Data
@Builder
public class APIError {

    private HttpStatus status;
    private String message;

}
