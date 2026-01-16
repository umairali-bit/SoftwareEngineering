package com.umair.librarymanagement.controllers;


import com.umair.librarymanagement.LibraryManagementSystemTestConfiguration;
import com.umair.librarymanagement.dtos.AuthorDTO;
import com.umair.librarymanagement.dtos.BookDTO;
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
    protected WebTestClient webTestClient;

    // DTO templates (fresh per test)
    protected AuthorDTO authorCreateDTO;
    protected AuthorDTO authorCreateDTO2;

    protected BookDTO bookCreateDTO;
    protected BookDTO bookCreateDTO2;

    protected BookDTO bookUpdateDTO;
    protected AuthorDTO authorUpdateDTO;

    @BeforeEach
    void setupTemplates() {
        authorCreateDTO = AuthorDTO.builder()
                .id(null)
                .name("Jessie Pinkman")
                .books(new HashSet<>())
                .build();

        authorCreateDTO2 = AuthorDTO.builder()
                .id(null)
                .name("Walter White")
                .books(new HashSet<>())
                .build();

        bookCreateDTO = BookDTO.builder()
                .id(null)
                .title("Breaking Bad")
                .publishedDate(LocalDate.of(2020, 1, 1))
                .author(null)
                .build();

        bookCreateDTO2 = BookDTO.builder()
                .id(null)
                .title("Better Call Saul")
                .publishedDate(LocalDate.of(2020, 5, 5))
                .author(null)
                .build();

        bookUpdateDTO = BookDTO.builder()
                .id(null)
                .title("Updated Title")
                .publishedDate(LocalDate.of(2022, 1, 1))
                .author(null)
                .build();

        authorUpdateDTO = AuthorDTO.builder()
                .id(null)
                .name("Updated Author Name")
                .build();
    }
}


