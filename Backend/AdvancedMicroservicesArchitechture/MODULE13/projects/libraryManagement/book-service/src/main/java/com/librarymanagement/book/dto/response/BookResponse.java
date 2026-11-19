package com.librarymanagement.book.dto.response;

import com.librarymanagement.book.entity.enums.Genre;

public record BookResponse(

        Long id,
        String isbnNumber,
        String title,
        String author,
        Genre genre,
        String publisher,
        Integer publicationYear,
        Integer totalCopies,
        Integer availableCopies

) {
}
