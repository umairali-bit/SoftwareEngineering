package com.umair.librarymanagement.services;

import com.umair.librarymanagement.LibraryManagementSystemTestConfiguration;
import com.umair.librarymanagement.dtos.AuthorDTO;
import com.umair.librarymanagement.dtos.BookSummaryDTO;
import com.umair.librarymanagement.entities.AuthorEntity;
import com.umair.librarymanagement.entities.BookEntity;
import com.umair.librarymanagement.repositories.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private AuthorEntity mockAuthorEntity;
    private AuthorDTO mockAuthorDto;

    private BookEntity mockBookEntity;
    private BookSummaryDTO mockBookSummaryDTO;

    @BeforeEach
    void setUp() {

//      Book Entity
        mockBookEntity = new BookEntity();
        mockBookEntity.setId(1L);
        mockBookEntity.setTitle("Breaking Bad");
        mockBookEntity.setPublishedDate(LocalDate.now());


//      Author Entity
        mockAuthorEntity = new AuthorEntity();
        mockAuthorEntity.setId(1L);
        mockAuthorEntity.setName("Jessie Pinkman");


//      DTO coming from API
        mockBookSummaryDTO = new BookSummaryDTO();
        mockBookSummaryDTO.setId(1L);
        mockBookSummaryDTO.setTitle("Breaking Bad");
        mockBookSummaryDTO.setPublishedDate(mockBookEntity.getPublishedDate());

        mockAuthorDto = new AuthorDTO();
        mockAuthorDto.setName("Jessie Pinkman");
        mockAuthorDto.getBooks().add(mockBookSummaryDTO);


    }


    @Test
    void createAuthor() {
    }

    @Test
    void getAllAuthors() {
    }

    @Test
    void getAuthor() {
    }

    @Test
    void deleteAuthor() {
    }

    @Test
    void findAuthorByName() {
    }

    @Test
    void updateAuthor() {
    }

    @Test
    void getBooksByAuthor() {
    }
}