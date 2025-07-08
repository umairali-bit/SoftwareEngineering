package com.example.SpringBootDevTools.SpringBootDevTools.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
