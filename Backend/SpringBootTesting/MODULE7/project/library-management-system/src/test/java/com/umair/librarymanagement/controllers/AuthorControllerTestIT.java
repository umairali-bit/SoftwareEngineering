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

//        create Book
        ApiResponse<BookDTO> bookResp = webTestClient.post()
                .uri("/api/books")
                .bodyValue(bookCreateDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(new ParameterizedTypeReference<ApiResponse<BookDTO>>() {})
                .returnResult()
                .getResponseBody();

        assertThat(bookResp).isNotNull();
        BookDTO createdBook = bookResp.getData();
        assertThat(createdBook).isNotNull();

//        Assertions
        assertThat(createdBook.getId()).isNotNull();
        assertThat(createdBook.getTitle()).isEqualTo(bookCreateDTO.getTitle());
        assertThat(createdBook.getPublishedDate()).isEqualTo(bookCreateDTO.getPublishedDate());
        assertThat(createdBook.getAuthor()).isNotNull();
        assertThat(createdBook.getAuthor().getId()).isEqualTo(createdAuthor.getId());

    }

    @Test
    void getAllAuthors_shouldReturnAllAuthors(){

//        creating authors
        ApiResponse<AuthorDTO> author1 = webTestClient.post()
                .uri("/api/authors")
                .bodyValue(authorCreateDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(new ParameterizedTypeReference<ApiResponse<AuthorDTO>>() {})
                .returnResult()
                .getResponseBody();

        ApiResponse<AuthorDTO> author2 = webTestClient.post()
                .uri("/api/authors")
                .bodyValue(authorCreateDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(new ParameterizedTypeReference<ApiResponse<AuthorDTO>>() {})
                .returnResult()
                .getResponseBody();

        assertThat(author1.getData().getName()).isEqualTo("Jessie Pinkman");
        assertThat(author2.getData().getName()).isEqualTo("Walter White");

        Long authorId1 = author1.getData().getId();
        Long authorId2 = author2.getData().getId();

//         creating books with different dates
        BookDTO oldBookReq = BookDTO.builder()
                .title("Breaking Bad")
                .publishedDate(LocalDate.of(2020,1,1))
                .author(AuthorSummaryDTO.builder().id(authorId1).build())
                .build();

        BookDTO newBookReq = BookDTO.builder()
                .title("Better Call Saul")
                .publishedDate(LocalDate.of(2020,5,5))
                .author(AuthorSummaryDTO.builder().id(authorId2).build())
                .build();

        webTestClient.post()
                .uri("/api/authors")
                .bodyValue(author1)
                .exchange()
                .expectStatus().isCreated();

        webTestClient.post()
                .uri("/api/authors")
                .bodyValue(author2)
                .exchange()
                .expectStatus().isCreated();



    }

}
