package com.umair.librarymanagement.exception;

public class AuthorNotFoundException extends  RuntimeException {
    public AuthorNotFoundException(Long id) {
        super("Author not found with ID: " + id);
    }
    public AuthorNotFoundException() {
        super("Author ID is required to create a book");
    }
}

