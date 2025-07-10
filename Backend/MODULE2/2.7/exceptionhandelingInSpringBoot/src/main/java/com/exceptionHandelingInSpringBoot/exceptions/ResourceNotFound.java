package com.exceptionHandelingInSpringBoot.exceptions;

import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;

public class ResourceNotFound extends RuntimeException{
    public ResourceNotFound(String message) {
        super(message);
    }
}
