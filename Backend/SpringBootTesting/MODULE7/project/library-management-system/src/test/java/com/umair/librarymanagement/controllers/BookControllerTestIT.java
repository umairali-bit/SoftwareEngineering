package com.umair.librarymanagement.controllers;

import com.umair.librarymanagement.advices.ApiResponse;
import com.umair.librarymanagement.dtos.AuthorDTO;
import com.umair.librarymanagement.dtos.AuthorSummaryDTO;
import com.umair.librarymanagement.dtos.BookDTO;
import com.umair.librarymanagement.repositories.AuthorRepository;
import com.umair.librarymanagement.repositories.BookRepository;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;

import java.awt.print.Book;
import java.time.LocalDate;
import java.util.List;

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

        AuthorDTO createdAuthor = authorDTOApiResponse.getData();
        assertThat(createdAuthor).isNotNull();

//   Attaching authorId to book create payload
        bookCreateDTO.setAuthor(
                AuthorSummaryDTO.builder()
                        .id(createdAuthor.getId())
                        .build()
        );

//        create book and assert response
        ApiResponse<BookDTO> bookDTOApiResponse = webTestClient.post()
                .uri("/api/books")
                .bodyValue(bookCreateDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(new ParameterizedTypeReference<ApiResponse<BookDTO>>() {})
                .returnResult()
                .getResponseBody();
        assertThat(bookDTOApiResponse).isNotNull();
        assertThat(bookDTOApiResponse.getData()).isNotNull();

        BookDTO createdBook = bookDTOApiResponse.getData();
        assertThat(createdBook.getId()).isNotNull();
        assertThat(createdBook.getTitle()).isEqualTo(bookCreateDTO.getTitle());
        assertThat(createdBook.getPublishedDate()).isEqualTo(bookCreateDTO.getPublishedDate());

        assertThat(createdBook.getAuthor()).isNotNull();
        assertThat(createdBook.getAuthor().getId()).isEqualTo(createdAuthor.getId());

    }

    @Test
    void getAllBooks_shouldReturnBooksSortedByPublishedDateDesc() {
//    creating authors
        ApiResponse<AuthorDTO> a1Resp = webTestClient.post()
                .uri("/api/authors")
                .bodyValue(authorCreateDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(new ParameterizedTypeReference<ApiResponse<AuthorDTO>>() {})
                .returnResult()
                .getResponseBody();

        ApiResponse<AuthorDTO> a2Resp = webTestClient.post()
                .uri("/api/authors")
                .bodyValue(authorCreateDTO2)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(new ParameterizedTypeReference<ApiResponse<AuthorDTO>>() {})
                .returnResult()
                .getResponseBody();

        assertThat(a1Resp.getData().getName()).isEqualTo("Jessie Pinkman");
        assertThat(a2Resp.getData().getName()).isEqualTo("Walter White");

        Long authorId1 = a1Resp.getData().getId();
        Long authorId2 = a1Resp.getData().getId();

//        creating books with different dates
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
                .uri("/api/books")
                .bodyValue(oldBookReq)
                .exchange()
                .expectStatus().isCreated();

        webTestClient.post()
                .uri("/api/books")
                .bodyValue(newBookReq)
                .exchange()
                .expectStatus().isCreated();

//        GET all books
        ApiResponse<List<BookDTO>> response = webTestClient.get()
                .uri("/api/books")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<ApiResponse<List<BookDTO>>>() {})
                .returnResult()
                .getResponseBody();







    }


}
