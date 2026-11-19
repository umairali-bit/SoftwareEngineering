package com.librarymanagement.book.dto.request;

import com.librarymanagement.book.entity.enums.Genre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateBookRequest(

        @NotBlank(message = "ISBN number is required")
        @Pattern(
                regexp = "\\d{10}",
                message = "ISBN must contain exactly 10 digits"
        )
        String isbnNumber,
        String title,
        String author,
        Genre genre,
        String publisher,
        Integer publicationYear,
        Integer totalCopies



) {
}
