package com.umair.librarymanagement.controllers;

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
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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

//        Assert: wrapper + list order
        assertThat(response).isNotNull();
        assertThat(response.getData()).isNotNull();

        List<BookDTO> result = response.getData();
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);

//        Newest first (DESC publishedDate)
        assertThat(result.get(0).getTitle()).isEqualTo("Better Call Saul");
        assertThat(result.get(0).getPublishedDate()).isEqualTo(LocalDate.of(2020,5,5));
        assertThat(result.get(0).getAuthor()).isNotNull();
        assertThat(result.get(0).getAuthor().getId()).isEqualTo(authorId2);

        assertThat(result.get(1).getTitle()).isEqualTo("Breaking Bad");
        assertThat(result.get(1).getPublishedDate()).isEqualTo(LocalDate.of(2020, 1, 1));
        assertThat(result.get(1).getAuthor()).isNotNull();
        assertThat(result.get(1).getAuthor().getId()).isEqualTo(authorId1);

    }

    @Test
    void updateBook_shouldUpdateTitleAndDate() {
//        Arrange
        ApiResponse<AuthorDTO> createdAuthor = webTestClient.post()
                .uri("/api/authors")
                .bodyValue(authorCreateDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(new ParameterizedTypeReference<ApiResponse<AuthorDTO>>() {})
                .returnResult()
                .getResponseBody();

        ApiResponse<AuthorDTO> createdAuthor2 = webTestClient.post()
                .uri("/api/authors")
                .bodyValue(authorCreateDTO2)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(new ParameterizedTypeReference<ApiResponse<AuthorDTO>>() {})
                .returnResult()
                .getResponseBody();

        assertThat(createdAuthor).isNotNull();
        assertThat(createdAuthor.getData()).isNotNull();

        assertThat(createdAuthor2).isNotNull();
        assertThat(createdAuthor2.getData()).isNotNull();

        Long  oldAuthorId = createdAuthor.getData().getId();
        Long  newAuthorId = createdAuthor2.getData().getId();



//        Create Book
        BookDTO createReq  = BookDTO.builder()
                .title("Old Book")
                .publishedDate(LocalDate.of(2020,1,1))
                .author(AuthorSummaryDTO.builder().id(oldAuthorId).build())
                .build();


        ApiResponse<BookDTO> createdBookResp = webTestClient.post()
                .uri("/api/books")
                .bodyValue(createReq)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(new ParameterizedTypeReference<ApiResponse<BookDTO>>() {})
                .returnResult()
                .getResponseBody();

        assertThat(createdBookResp).isNotNull();
        assertThat(createdBookResp.getData()).isNotNull();

        Long bookId = createdBookResp.getData().getId();

//        arrange: update payload
        BookDTO updateReq = BookDTO.builder()
                .title("New Book")
                .publishedDate(LocalDate.of(2022,5,5))
                .author(AuthorSummaryDTO.builder().id(newAuthorId).build())
                .build();

//        Act
        ApiResponse<BookDTO> updatedBookResp = webTestClient.put()
                .uri("/api/books/{id}", bookId)
                .bodyValue(updateReq)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<ApiResponse<BookDTO>>() {})
                .returnResult()
                .getResponseBody();

//        Assertions
        assertThat(updatedBookResp).isNotNull();
        assertThat(updatedBookResp.getData()).isNotNull();

        BookDTO updatedBook = updatedBookResp.getData();
        assertThat(updatedBook.getId()).isEqualTo(bookId);
        assertThat(updatedBook.getTitle()).isEqualTo("New Book");
        assertThat(updatedBook.getPublishedDate()).isEqualTo(LocalDate.of(2022,5,5));
        assertThat(updatedBook.getAuthor()).isNotNull();
        assertThat(updatedBook.getAuthor().getId()).isEqualTo(newAuthorId);

//        checking if old author no longer has the book
        ApiResponse<AuthorDTO> oldAuthorAfter = webTestClient.get()
                .uri("/api/authors/{id}", oldAuthorId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<ApiResponse<AuthorDTO>>() {})
                .returnResult()
                .getResponseBody();

        assertThat(oldAuthorAfter).isNotNull();
        assertThat(oldAuthorAfter.getData()).isNotNull();
        assertThat(oldAuthorAfter.getData().getBooks())
                .isNotNull()
                .noneMatch(b -> b.getId().equals(bookId));


//      verify new author now contains the book
        ApiResponse<AuthorDTO> newAuthorAfter = webTestClient.get()
                .uri("/api/authors/{id}", newAuthorId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<ApiResponse<AuthorDTO>>() {})
                .returnResult()
                .getResponseBody();

        List<Long> newAuthorBookIds = newAuthorAfter.getData().getBooks().stream()
                .map(b-> b.getId())
                .toList();

        assertThat(newAuthorBookIds).contains(bookId);


    }




}
