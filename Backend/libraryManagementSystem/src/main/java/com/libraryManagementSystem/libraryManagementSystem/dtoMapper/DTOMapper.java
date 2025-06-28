package com.libraryManagementSystem.libraryManagementSystem.dtoMapper;

import com.libraryManagementSystem.libraryManagementSystem.dto.AuthorDTO;
import com.libraryManagementSystem.libraryManagementSystem.dto.BookDTO;
import com.libraryManagementSystem.libraryManagementSystem.entities.AuthorEntity;
import com.libraryManagementSystem.libraryManagementSystem.entities.BookEntity;

import java.util.List;
import java.util.stream.Collectors;

public class DTOMapper {

    public static BookDTO convertToBookDTO (BookEntity book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setPublishedDate(book.getPublishedDate());

        if (book.getAuthors() != null) {
            AuthorDTO authorDTO = new AuthorDTO();
            authorDTO.setId(authorDTO.getId());
            authorDTO.setName(authorDTO.getName());
            dto.setAuthors(List.of(authorDTO));
        }
        return dto;
    }

    public static AuthorDTO convertToAuthorDTO(AuthorEntity author) {
        AuthorDTO dto = new AuthorDTO();
        dto.setId(author.getId());
        dto.setName(author.getName());


        List<BookDTO> bookDTOs = author.getBooks().stream().map(
                book -> {
                    BookDTO bookDTO = new BookDTO();
                    bookDTO.setId(book.getId());
                    bookDTO.setTitle(bookDTO.getTitle());
                    bookDTO.setPublishedDate(bookDTO.getPublishedDate());
                    return bookDTO;
                }).collect(Collectors.toList());

        dto.setBooks(bookDTOs);
        return dto;

    }
}





