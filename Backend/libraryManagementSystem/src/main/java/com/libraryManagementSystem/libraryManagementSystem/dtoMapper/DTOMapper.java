package com.libraryManagementSystem.libraryManagementSystem.dtoMapper;

import com.libraryManagementSystem.libraryManagementSystem.dto.AuthorDTO;
import com.libraryManagementSystem.libraryManagementSystem.dto.BookDTO;
import com.libraryManagementSystem.libraryManagementSystem.entities.AuthorEntity;
import com.libraryManagementSystem.libraryManagementSystem.entities.BookEntity;
import com.libraryManagementSystem.libraryManagementSystem.repositories.AuthorRepository;

import java.util.List;
import java.util.stream.Collectors;

public class DTOMapper {

    public static BookDTO convertToBookDTO (BookEntity book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setPublishedDate(book.getPublishedDate());

        if (book.getAuthors() != null) {
        List<AuthorDTO> authorDTOS = book.getAuthors().stream().map(
                author -> {
                    AuthorDTO authorDTO = new AuthorDTO();
                    authorDTO.setId(author.getId());
                    authorDTO.setName(author.getName());
                    return authorDTO;
                }).collect(Collectors.toList());
        dto.setAuthors(authorDTOS);

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
                    bookDTO.setTitle(book.getTitle());
                    bookDTO.setPublishedDate(book.getPublishedDate());
                    return bookDTO;
                }).collect(Collectors.toList());

        dto.setBooks(bookDTOs);
        return dto;

    }

    public static BookEntity convertToBookEntity(BookDTO dto, AuthorRepository authorRepository) {
        BookEntity book = new BookEntity();
        book.setTitle(dto.getTitle());
        book.setPublishedDate(dto.getPublishedDate());

        for (AuthorDTO authorDTO : dto.getAuthors()) {
            AuthorEntity author = authorRepository.findById(authorDTO.getId())
                    .orElseThrow(() -> new RuntimeException("Author not found"));
            book.addAuthor(author);
        }
        return book;
    }
}






