package com.umair.librarymanagement.controllers;


import com.umair.librarymanagement.LibraryManagementSystemTestConfiguration;
import com.umair.librarymanagement.dtos.AuthorDTO;
import com.umair.librarymanagement.dtos.AuthorSummaryDTO;
import com.umair.librarymanagement.dtos.BookDTO;
import com.umair.librarymanagement.dtos.BookSummaryDTO;
import com.umair.librarymanagement.entities.AuthorEntity;
import com.umair.librarymanagement.entities.BookEntity;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@AutoConfigureWebTestClient(timeout = "100000")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(LibraryManagementSystemTestConfiguration.class)
public abstract class AbstractIntegrationTest {

    @Autowired
    WebTestClient webTestClient;

//  Entities
    protected AuthorEntity author = AuthorEntity.builder()
            .id(1L)
            .name("Jessie Pinkman")
            .books(new HashSet<>())
            .build();
     
    protected AuthorEntity author2 = AuthorEntity.builder()
            .id(2L)
            .name("Walter White")
            .books(new HashSet<>())
            .build();
    protected BookEntity book = BookEntity.builder()
            .id(1L)
            .title("Breaking Bad")
            .publishedDate(LocalDate.of(2020,1,1))
            .author(author)
            .build();
    protected BookEntity book2 = BookEntity.builder()
            .id(2L)
            .title("Better Call Saul")
            .publishedDate(LocalDate.of(2020,5,5))
            .author(author2)
            .build();

//  DTO templates
    protected AuthorDTO authorCreateDTO = AuthorDTO.builder()
            .name(author.getName())
            .books(Set.of()) // keep create payload simple
            .build();

    protected AuthorDTO authorCreateDTO2 = AuthorDTO.builder()
            .name(author2.getName())
            .books(Set.of())
            .build();

//     For createBook
    protected BookDTO bookCreateDTO = BookDTO.builder()
            .title(book.getTitle())
            .publishedDate(book.getPublishedDate())
            .author(null)
            .build();

    protected BookDTO bookCreateDTO2 = BookDTO.builder()
            .title(book2.getTitle())
            .publishedDate(book2.getPublishedDate())
            .author(null)
            .build();

//     Update templates
    protected BookDTO bookUpdateDTO = BookDTO.builder()
            .title("Updated Title")
            .publishedDate(LocalDate.of(2022, 1, 1))
            .author(null)
            .build();

    protected AuthorDTO authorUpdateDTO = AuthorDTO.builder()
            .name("Updated Author Name")
            .build();

    @BeforeEach
    void linkBidirectional() {
        author.getBooks().add(book);
        author2.getBooks().add(book2);
    }


    }



}
