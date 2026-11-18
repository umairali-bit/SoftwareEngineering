package com.librarymanagement.book.dto.request;

import jakarta.validation.constraints.NotBlank;

public record BookRequest(

        @NotBlank
        String isbnNumber
) {
}
