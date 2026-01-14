package com.umair.librarymanagement.controllers;

import com.umair.librarymanagement.advices.ApiResponse;
import com.umair.librarymanagement.dtos.AuthorDTO;
import com.umair.librarymanagement.dtos.AuthorSummaryDTO;
import com.umair.librarymanagement.dtos.BookDTO;
import com.umair.librarymanagement.repositories.AuthorRepository;
import com.umair.librarymanagement.repositories.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BookControllerTestIT extends AbstractIntegrationTest{

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    void setup() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();
    }

    @Test
    void createBook_shouldCreateBookWithAuthor() {

//  API response is wrapped
        ApiResponse<AuthorDTO> authorDTOApiResponse = webTestClient.post()
                .uri("/api/authors")
                .bodyValue(authorCreateDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(new ParameterizedTypeReference<ApiResponse<AuthorDTO>>() {})
                .returnResult()
                .getResponseBody();

        assertThat(authorDTOApiResponse).isNotNull();
        assertThat(authorDTOApiResponse.getData()).isNotNull();

        AuthorDTO authorDTO = authorDTOApiResponse.getData();
        assertThat(authorDTO).isNotNull();

//   Attaching authorId to book create payload
        bookCreateDTO.setAuthor(
                AuthorSummaryDTO.builder()
                        .id(authorDTO.getId())
                        .build()
        );


    }
}
