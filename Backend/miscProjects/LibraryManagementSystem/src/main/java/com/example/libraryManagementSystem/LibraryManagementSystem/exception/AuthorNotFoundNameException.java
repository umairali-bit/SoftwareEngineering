package com.example.libraryManagementSystem.LibraryManagementSystem.exception;

public class AuthorNotFoundNameException extends  RuntimeException {
    public AuthorNotFoundNameException(String name) {
        super("Author not found with the name: " + name);
    }
}
