package com.example.libraryManagementSystem.LibraryManagementSystem.services;

import com.example.libraryManagementSystem.LibraryManagementSystem.dtos.AuthorDTO;
import com.example.libraryManagementSystem.LibraryManagementSystem.dtos.AuthorSummaryDTO;
import com.example.libraryManagementSystem.LibraryManagementSystem.dtos.BookDTO;
import com.example.libraryManagementSystem.LibraryManagementSystem.dtos.BookSummaryDTO;
import com.example.libraryManagementSystem.LibraryManagementSystem.entities.AuthorEntity;
import com.example.libraryManagementSystem.LibraryManagementSystem.entities.BookEntity;

import java.util.Set;
import java.util.stream.Collectors;

public class Mapper {


    public static AuthorDTO mapToDTO(AuthorEntity author) {

        if (author == null) {
            return null;
        }

        AuthorDTO dto = new AuthorDTO();
        dto.setId(author.getId());
        dto.setName(author.getName());

        Set<BookSummaryDTO> bookSummaries = author.getBooks().stream().
                map(s -> new BookSummaryDTO(
                        s.getId(),
                        s.getTitle(),
                        s.getPublishedDate()
                ))
                .collect(Collectors.toSet());

        dto.setBooks(bookSummaries);

        return dto;

    }

    public static BookDTO mapToDTO(BookEntity book) {
        if (book == null) {
            return null;
        }
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setPublishedDate(book.getPublishedDate());

        if (book.getAuthor() != null) {
            dto.setAuthor(new AuthorSummaryDTO(
                    book.getAuthor().getId(),
                    book.getAuthor().getName()
            ));
        }
        return dto;

    }
}
