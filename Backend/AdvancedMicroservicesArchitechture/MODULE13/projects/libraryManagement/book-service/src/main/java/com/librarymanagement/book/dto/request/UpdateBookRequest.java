package com.librarymanagement.book.dto.request;

import com.librarymanagement.book.entity.enums.Genre;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateBookRequest (

        @NotBlank
        String title,

        @NotBlank
        String author,

        @NotBlank
        Genre genre,

        String publisher,

        @NotNull
        @Min(1450)
        Integer publicationYear


){
}
