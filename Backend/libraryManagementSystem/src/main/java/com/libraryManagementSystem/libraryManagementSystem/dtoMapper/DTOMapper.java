
package com.libraryManagementSystem.libraryManagementSystem.dtoMapper;

import com.libraryManagementSystem.libraryManagementSystem.dto.AuthorDTO;
import com.libraryManagementSystem.libraryManagementSystem.dto.BookDTO;
import com.libraryManagementSystem.libraryManagementSystem.entities.AuthorEntity;
import com.libraryManagementSystem.libraryManagementSystem.entities.BookEntity;
import com.libraryManagementSystem.libraryManagementSystem.repositories.AuthorRepository;

import java.util.List;
import java.util.stream.Collectors;

public class DTOMapper {

    public static BookDTO convertToBookDTO(BookEntity book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setPublishedDate(book.getPublishedDate());

        if (book.getAuthors() != null) {
            List<AuthorDTO> authors = book.getAuthors().stream().map(author -> {
                AuthorDTO authorDTO = new AuthorDTO();
                authorDTO.setId(author.getId());
                authorDTO.setName(author.getName());
                // **DON'T set books here to avoid infinite recursion**
                // authorDTO.setBooks(...) <-- omit this
                return authorDTO;
            }).collect(Collectors.toList());
            dto.setAuthors(authors);
        }

        return dto;
    }

    public static AuthorDTO convertToAuthorDTO(AuthorEntity author) {
        AuthorDTO dto = new AuthorDTO();
        dto.setId(author.getId());
        dto.setName(author.getName());

        if (author.getBooks() != null) {
            List<BookDTO> bookDTOs = author.getBooks().stream().map(book -> {
                BookDTO bookDTO = new BookDTO();
                bookDTO.setId(book.getId());
                bookDTO.setTitle(book.getTitle());
                bookDTO.setPublishedDate(book.getPublishedDate());
                // **DON'T set authors here to avoid infinite recursion**
                // bookDTO.setAuthors(...) <-- omit this
                return bookDTO;
            }).collect(Collectors.toList());
            dto.setBooks(bookDTOs);
        }

        return dto;
    }

}
