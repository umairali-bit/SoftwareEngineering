package com.example.libraryManagementSystem.LibraryManagementSystem.exception;

public class AuthorNotFoundException extends  RuntimeException {
    public AuthorNotFoundException(Long id) {
        super("Author not found with ID: " + id);
    }
}
