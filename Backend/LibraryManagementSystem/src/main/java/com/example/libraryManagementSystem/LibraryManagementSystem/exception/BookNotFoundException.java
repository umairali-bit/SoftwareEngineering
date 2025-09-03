package com.example.libraryManagementSystem.LibraryManagementSystem.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(Long id) {
        super("Book not found with ID: " + id);
    }
}
