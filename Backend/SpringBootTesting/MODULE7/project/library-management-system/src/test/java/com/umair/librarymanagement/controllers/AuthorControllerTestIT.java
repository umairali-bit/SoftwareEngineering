package com.umair.librarymanagement.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.umair.librarymanagement.advices.ApiResponse;
import com.umair.librarymanagement.dtos.AuthorDTO;
import com.umair.librarymanagement.dtos.AuthorSummaryDTO;
import com.umair.librarymanagement.dtos.BookDTO;
import com.umair.librarymanagement.dtos.BookSummaryDTO;
import com.umair.librarymanagement.repositories.AuthorRepository;
import com.umair.librarymanagement.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;

import java.time.LocalDate;
import java.util.Set;

public class AuthorControllerTestIT extends AbstractIntegrationTest{
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setup() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();
    }
    @Test
    void createAuthor_shouldCreateAuthorWithBook(){
//        Arrange: creating author and book
        ApiResponse<AuthorDTO> createdAuthorResp = webTestClient.post()
                .uri("/api/authors")
                .bodyValue(authorCreateDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(new ParameterizedTypeReference<ApiResponse<AuthorDTO>>() {})
                .returnResult()
                .getResponseBody();

        assertThat(createdAuthorResp).isNotNull();
        assertThat(createdAuthorResp.getData()).isNotNull();

        AuthorDTO createdAuthor = createdAuthorResp.getData();
        assertThat(createdAuthor).isNotNull();

//        attaching authorId to book request
        bookCreateDTO.setAuthor(
                AuthorSummaryDTO.builder()
                        .id(createdAuthor.getId())
                        .build()
        );








    }

}
