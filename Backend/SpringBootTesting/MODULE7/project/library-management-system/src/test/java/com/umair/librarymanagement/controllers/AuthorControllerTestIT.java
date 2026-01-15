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
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;
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
                .bodyValue(authorCreateDTO2)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(new ParameterizedTypeReference<ApiResponse<AuthorDTO>>() {})
                .returnResult()
                .getResponseBody();

            assertThat(author1).isNotNull();
            assertThat(author2).isNotNull();
            assertThat(author1.getData()).isNotNull();
            assertThat(author2.getData()).isNotNull();

            Long authorId1 = author1.getData().getId();
            Long authorId2 = author2.getData().getId();

            bookCreateDTO.setAuthor(AuthorSummaryDTO.builder().id(authorId1).build());
            bookCreateDTO2.setAuthor(AuthorSummaryDTO.builder().id(authorId2).build());

//        creating books
       ApiResponse<BookDTO> book1 = webTestClient.post()
               .uri("/api/books")
               .bodyValue(bookCreateDTO)
               .exchange()
               .expectStatus().isCreated()
               .expectBody(new ParameterizedTypeReference<ApiResponse<BookDTO>>() {})
               .returnResult()
               .getResponseBody();
        ApiResponse<BookDTO> book2 = webTestClient.post()
                .uri("/api/books")
                .bodyValue(bookCreateDTO2)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(new ParameterizedTypeReference<ApiResponse<BookDTO>>() {})
                .returnResult()
                .getResponseBody();


        assertThat(book1).isNotNull();
        assertThat(book2).isNotNull();
        assertThat(book1.getData()).isNotNull();
        assertThat(book2.getData()).isNotNull();


//        GET all authors
        ApiResponse<List<AuthorDTO>> response = webTestClient.get()
                .uri("/api/authors")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<ApiResponse<List<AuthorDTO>>>() {})
                .returnResult()
                .getResponseBody();


//        Assert
        assertThat(response).isNotNull();
        assertThat(response.getData()).isNotNull();

        List<AuthorDTO> result = response.getData();
        assertThat(result)
                .isNotNull()
                .hasSize(2)
                .extracting(AuthorDTO::getName)
                .containsExactlyInAnyOrder(
                        "Jessie Pinkman",
                        "Walter White"
                );

//         Assert books are attached to the right authors
        AuthorDTO jessie = result.stream()
                .filter(a -> "Jessie Pinkman".equals(a.getName()))
                .findFirst()
                .orElseThrow();

        AuthorDTO walter = result.stream()
                .filter(a -> "Walter White".equals(a.getName()))
                .findFirst()
                .orElseThrow();

        assertThat(jessie.getBooks())
                .isNotNull()
                .extracting(BookSummaryDTO::getTitle)
                .contains("Breaking Bad");

        assertThat(walter.getBooks())
                .isNotNull()
                .extracting(BookSummaryDTO::getTitle)
                .contains("Better Call Saul");
    }

    @Test
    void getAuthorById_shouldReturnAuthor(){
//        creating author
        ApiResponse<AuthorDTO> a1 = webTestClient.post()
                .uri("/api/authors")
                .bodyValue(authorCreateDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(new ParameterizedTypeReference<ApiResponse<AuthorDTO>>() {})
                .returnResult()
                .getResponseBody();

        assertThat(a1).isNotNull();
        assertThat(a1.getData()).isNotNull();

        Long authorId = a1.getData().getId();

//        attaching the book to author
        bookCreateDTO.setAuthor(AuthorSummaryDTO.builder().id(authorId).build());

//        creating book
        ApiResponse<BookDTO> book = webTestClient.post()
                .uri("/api/books")
                .bodyValue(bookCreateDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(new ParameterizedTypeReference<ApiResponse<BookDTO>>() {})
                .returnResult()
                .getResponseBody();

        assertThat(book).isNotNull();
        assertThat(book.getData()).isNotNull();


    }


}
