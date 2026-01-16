package com.umair.librarymanagement.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.umair.librarymanagement.advices.ApiResponse;
import com.umair.librarymanagement.dtos.AuthorDTO;
import com.umair.librarymanagement.dtos.AuthorSummaryDTO;
import com.umair.librarymanagement.dtos.BookDTO;
import com.umair.librarymanagement.dtos.BookSummaryDTO;
import com.umair.librarymanagement.repositories.AuthorRepository;
import com.umair.librarymanagement.repositories.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;

import java.awt.print.Book;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class AuthorControllerTestIT extends AbstractIntegrationTest {
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
    void createAuthor_shouldCreateAuthorWithBook() {
//        Arrange: creating author and book
        ApiResponse<AuthorDTO> createdAuthorResp = webTestClient.post()
                .uri("/api/authors")
                .bodyValue(authorCreateDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(new ParameterizedTypeReference<ApiResponse<AuthorDTO>>() {
                })
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
                .expectBody(new ParameterizedTypeReference<ApiResponse<BookDTO>>() {
                })
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
    void getAllAuthors_shouldReturnAllAuthors() {

//        creating authors
        ApiResponse<AuthorDTO> author1 = webTestClient.post()
                .uri("/api/authors")
                .bodyValue(authorCreateDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(new ParameterizedTypeReference<ApiResponse<AuthorDTO>>() {
                })
                .returnResult()
                .getResponseBody();

        ApiResponse<AuthorDTO> author2 = webTestClient.post()
                .uri("/api/authors")
                .bodyValue(authorCreateDTO2)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(new ParameterizedTypeReference<ApiResponse<AuthorDTO>>() {
                })
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
                .expectBody(new ParameterizedTypeReference<ApiResponse<BookDTO>>() {
                })
                .returnResult()
                .getResponseBody();
        ApiResponse<BookDTO> book2 = webTestClient.post()
                .uri("/api/books")
                .bodyValue(bookCreateDTO2)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(new ParameterizedTypeReference<ApiResponse<BookDTO>>() {
                })
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
                .expectBody(new ParameterizedTypeReference<ApiResponse<List<AuthorDTO>>>() {
                })
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
    void getAuthorById_shouldReturnAuthor() {
//        creating author
        ApiResponse<AuthorDTO> a1 = webTestClient.post()
                .uri("/api/authors")
                .bodyValue(authorCreateDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(new ParameterizedTypeReference<ApiResponse<AuthorDTO>>() {
                })
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
                .expectBody(new ParameterizedTypeReference<ApiResponse<BookDTO>>() {
                })
                .returnResult()
                .getResponseBody();

        assertThat(book).isNotNull();
        assertThat(book.getData()).isNotNull();


        ApiResponse<AuthorDTO> response = webTestClient.get()
                .uri("/api/authors/{id}", authorId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<ApiResponse<AuthorDTO>>() {
                })
                .returnResult()
                .getResponseBody();

        assertThat(response).isNotNull();
        assertThat(response.getData()).isNotNull();
        AuthorDTO author = response.getData();
        assertThat(author.getId()).isEqualTo(authorId);
        assertThat(author.getBooks())
                .extracting(
                        a -> a.getId(),
                        b -> b.getTitle(),
                        c -> c.getPublishedDate()
                )
                .containsExactly(
                        tuple(
                                book.getData().getId(),
                                book.getData().getTitle(),
                                book.getData().getPublishedDate()

                        )
                );


    }

    @Test
    void deleteAuthorById_shouldDeleteAuthor() {

//         create author
        ApiResponse<AuthorDTO> a1 = webTestClient.post()
                .uri("/api/authors")
                .bodyValue(authorCreateDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(new ParameterizedTypeReference<ApiResponse<AuthorDTO>>() {
                })
                .returnResult()
                .getResponseBody();

        assertThat(a1).isNotNull();
        assertThat(a1.getData()).isNotNull();
        Long authorId = a1.getData().getId();

//         attach author to book request
        bookCreateDTO.setAuthor(AuthorSummaryDTO.builder().id(authorId).build());

//         create book
        ApiResponse<BookDTO> book = webTestClient.post()
                .uri("/api/books")
                .bodyValue(bookCreateDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(new ParameterizedTypeReference<ApiResponse<BookDTO>>() {
                })
                .returnResult()
                .getResponseBody();

        assertThat(book).isNotNull();
        assertThat(book.getData()).isNotNull();
        Long bookId = book.getData().getId();
        assertThat(bookId).isNotNull();


//         delete author (204 = no body)
        webTestClient.delete()
                .uri("/api/authors/{id}", authorId)
                .exchange()
                .expectStatus().isNoContent()
                .expectBody().isEmpty();

//         assert author is gone
        webTestClient.get()
                .uri("/api/authors/{id}", authorId)
                .exchange()
                .expectStatus().isNotFound();

// since GET /api/books/{id} does not exist, fetch all books and find ours
        ApiResponse<List<BookDTO>> booksAfterResp = webTestClient.get()
                .uri("/api/books")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<ApiResponse<List<BookDTO>>>() {
                })
                .returnResult()
                .getResponseBody();

        assertThat(booksAfterResp).isNotNull();
        assertThat(booksAfterResp.getData()).isNotNull();

        List<BookDTO> booksAfter = booksAfterResp.getData();

        BookDTO fetchedBook = booksAfter.stream()
                .filter(b -> bookId.equals(b.getId()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Expected book with id " + bookId + " to still exist"));

        assertThat(fetchedBook.getAuthor()).isNull();
    }

    @Test
    void getAuthorByName_shouldReturnAuthor() {

//         create author
        ApiResponse<AuthorDTO> a1 = webTestClient.post()
                .uri("/api/authors")
                .bodyValue(authorCreateDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(new ParameterizedTypeReference<ApiResponse<AuthorDTO>>() {
                })
                .returnResult()
                .getResponseBody();

        assertThat(a1).isNotNull();
        assertThat(a1.getData()).isNotNull();

        AuthorDTO createdAuthor = a1.getData();
        Long authorId = createdAuthor.getId();
        String authorName = createdAuthor.getName();

        assertThat(authorId).isNotNull();
        assertThat(authorName).isNotBlank();

        bookCreateDTO.setAuthor(AuthorSummaryDTO.builder().id(authorId).build());


//         create book
        ApiResponse<BookDTO> book = webTestClient.post()
                .uri("/api/books")
                .bodyValue(bookCreateDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(new ParameterizedTypeReference<ApiResponse<BookDTO>>() {
                })
                .returnResult()
                .getResponseBody();

        assertThat(book).isNotNull();
        assertThat(book.getData()).isNotNull();

        BookDTO fetchedBook = book.getData();

        ApiResponse<AuthorDTO> fetchedAuthor = webTestClient.get()
                .uri("/api/authors/by-name/{name}", authorName)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<ApiResponse<AuthorDTO>>() {})
                .returnResult()
                .getResponseBody();



//        Assertions
        assertThat(fetchedAuthor).isNotNull();
        assertThat(fetchedAuthor.getData().getId()).isEqualTo(authorId);
        assertThat(fetchedAuthor.getData().getName()).isEqualTo(authorName);

//        verify books exist + contain the created one (id/title/date)
        assertThat(fetchedAuthor.getData().getBooks())
                .isNotNull()
                .extracting(
                        b-> b.getId(),
                        b-> b.getTitle(),
                        b-> b.getPublishedDate()
                )
                .contains(
                        tuple(
                                fetchedBook.getId(),
                                fetchedBook.getTitle(),
                                fetchedBook.getPublishedDate()
                        )
                );

    }

    @Test
    void updateAuthor_shouldUpdateNameOnly_whenBooksIsNull() {
//         create author
        ApiResponse<AuthorDTO> a1 = webTestClient.post()
                .uri("/api/authors")
                .bodyValue(authorCreateDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(new ParameterizedTypeReference<ApiResponse<AuthorDTO>>() {
                })
                .returnResult()
                .getResponseBody();

        assertThat(a1).isNotNull();
        assertThat(a1.getData()).isNotNull();

        AuthorDTO createdAuthor = a1.getData();
        Long authorId = createdAuthor.getId();
        assertThat(authorId).isNotNull();

        bookCreateDTO.setAuthor(AuthorSummaryDTO.builder().id(authorId).build());
        bookCreateDTO2.setAuthor(AuthorSummaryDTO.builder().id(authorId).build());

        //          create book
        ApiResponse<BookDTO> book = webTestClient.post()
                .uri("/api/books")
                .bodyValue(bookCreateDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(new ParameterizedTypeReference<ApiResponse<BookDTO>>() {
                })
                .returnResult()
                .getResponseBody();

        assertThat(book).isNotNull();
        assertThat(book.getData()).isNotNull();
        Long book1Id = book.getData().getId();
        assertThat(book1Id).isNotNull();

        ApiResponse<BookDTO> book2 = webTestClient.post()
                .uri("/api/books")
                .bodyValue(bookCreateDTO2)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(new ParameterizedTypeReference<ApiResponse<BookDTO>>() {
                })
                .returnResult()
                .getResponseBody();

        assertThat(book2).isNotNull();
        assertThat(book2.getData()).isNotNull();
        Long book2Id = book2.getData().getId();
        assertThat(book2Id).isNotNull();



//          ACT
        AuthorDTO updateReq = AuthorDTO.builder()
                .name("New Name")
                .books(null)
                .build();

        ApiResponse<AuthorDTO> updateAuthor = webTestClient.put()
                .uri("/api/authors/{authorId}", authorId)
                .bodyValue(updateReq)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<ApiResponse<AuthorDTO>>() {})
                .returnResult()
                .getResponseBody();

//          Assert
        assertThat(updateAuthor).isNotNull();
        assertThat(updateAuthor.getData()).isNotNull();

        assertThat(updateAuthor.getData().getId()).isEqualTo(authorId);
        assertThat(updateAuthor.getData().getName()).isEqualTo("New Name");

        ApiResponse<AuthorDTO> fetchedAuthorResp = webTestClient.get()
                .uri("/api/authors/{id}", authorId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<ApiResponse<AuthorDTO>>() {})
                .returnResult()
                .getResponseBody();

        assertThat(fetchedAuthorResp).isNotNull();
        assertThat(fetchedAuthorResp.getData()).isNotNull();
        assertThat(fetchedAuthorResp.getData().getBooks()).isNotNull();

        assertThat(fetchedAuthorResp.getData().getBooks())
                .extracting(BookSummaryDTO::getId)
                .containsExactly(book1Id, book2Id);



    }

    @Test
    void updateAuthor_shouldReplaceBooks_whenBooksProvided() {

//         create author
        ApiResponse<AuthorDTO> a1 = webTestClient.post()
                .uri("/api/authors")
                .bodyValue(authorCreateDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(new ParameterizedTypeReference<ApiResponse<AuthorDTO>>() {
                })
                .returnResult()
                .getResponseBody();

        assertThat(a1).isNotNull();
        assertThat(a1.getData()).isNotNull();

        AuthorDTO createdAuthor = a1.getData();
        Long authorId = createdAuthor.getId();
        assertThat(authorId).isNotNull();

        bookCreateDTO.setAuthor(AuthorSummaryDTO.builder().id(authorId).build());
        bookCreateDTO2.setAuthor(AuthorSummaryDTO.builder().id(authorId).build());

//          create book
        ApiResponse<BookDTO> book1 = webTestClient.post()
                .uri("/api/books")
                .bodyValue(bookCreateDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(new ParameterizedTypeReference<ApiResponse<BookDTO>>() {
                })
                .returnResult()
                .getResponseBody();

        assertThat(book1).isNotNull();
        assertThat(book1.getData()).isNotNull();

        ApiResponse<BookDTO> book2 = webTestClient.post()
                .uri("/api/books")
                .bodyValue(bookCreateDTO2)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(new ParameterizedTypeReference<ApiResponse<BookDTO>>() {
                })
                .returnResult()
                .getResponseBody();

        assertThat(book2).isNotNull();
        assertThat(book2.getData()).isNotNull();

        Long book1Id = book1.getData().getId();
        Long book2Id = book2.getData().getId();
        assertThat(book1Id).isNotNull();
        assertThat(book2Id).isNotNull();

        ApiResponse<AuthorDTO> tempAuthorResp = webTestClient.post()
                .uri("/api/authors")
                .bodyValue(authorCreateDTO2) // a different author payload
                .exchange()
                .expectStatus().isCreated()
                .expectBody(new ParameterizedTypeReference<ApiResponse<AuthorDTO>>() {})
                .returnResult()
                .getResponseBody();

        assertThat(tempAuthorResp).isNotNull();
        assertThat(tempAuthorResp.getData()).isNotNull();

        Long tempAuthorId = tempAuthorResp.getData().getId();
        assertThat(tempAuthorId).isNotNull();

//        Create Book 3
        BookDTO bookCreateDTO3  = BookDTO.builder()
                .title("Old Book")
                .publishedDate(LocalDate.of(2020,1,1))
                .author(AuthorSummaryDTO.builder().id(tempAuthorId).build())
                .build();


        ApiResponse<BookDTO> book3Resp = webTestClient.post()
                .uri("/api/books")
                .bodyValue(bookCreateDTO3)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(new ParameterizedTypeReference<ApiResponse<BookDTO>>() {})
                .returnResult()
                .getResponseBody();

        assertThat(book3Resp).isNotNull();
        assertThat(book3Resp.getData()).isNotNull();

        BookDTO createdBook3 = book3Resp.getData();
        Long book3Id = createdBook3.getId();
        assertThat(book3Id).isNotNull();

//          Build the "books" update payload using BookSummaryDTO ids
        BookSummaryDTO book3Summary = BookSummaryDTO.builder()
                .id(book3Id)
                .title(createdBook3.getTitle())
                .publishedDate(createdBook3.getPublishedDate())
                .build();

//          Act: replace books with ONLY book3 ----------
        AuthorDTO updateReq = AuthorDTO.builder()
                .name("New Name")
                .books(Set.of(book3Summary)) // NOT NULL -> triggers replace logic
                .build();

        ApiResponse<AuthorDTO> updateResp = webTestClient.put()
                .uri("/api/authors/{authorId}", authorId)
                .bodyValue(updateReq)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<ApiResponse<AuthorDTO>>() {})
                .returnResult()
                .getResponseBody();

        assertThat(updateResp).isNotNull();
        assertThat(updateResp.getData()).isNotNull();

//          Assert: author now contains ONLY book3
        ApiResponse<AuthorDTO> fetchedAuthorResp = webTestClient.get()
                .uri("/api/authors/{id}", authorId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<ApiResponse<AuthorDTO>>() {})
                .returnResult()
                .getResponseBody();

        assertThat(fetchedAuthorResp).isNotNull();
        assertThat(fetchedAuthorResp.getData()).isNotNull();
        assertThat(fetchedAuthorResp.getData().getBooks()).isNotNull();

        assertThat(fetchedAuthorResp.getData().getName()).isEqualTo("New Name");
        assertThat(fetchedAuthorResp.getData().getBooks())
                .extracting(BookSummaryDTO::getId)
                .containsExactly(book3Id);

//          Assert: old books are unlinked
        // No GET /api/books/{id} exists; use GET /api/books and filter.
        ApiResponse<List<BookDTO>> booksAfterResp = webTestClient.get()
                .uri("/api/books")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<ApiResponse<List<BookDTO>>>() {})
                .returnResult()
                .getResponseBody();



        assertThat(booksAfterResp).isNotNull();
        assertThat(booksAfterResp.getData()).isNotNull();

        List<BookDTO> booksAfter = booksAfterResp.getData();
        assertThat(booksAfter).isNotEmpty();

        BookDTO book1After = booksAfter.stream()
                .filter(b -> book1Id.equals(b.getId()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Expected book1 to exist"));

        BookDTO book2After = booksAfter.stream()
                .filter(b -> book2Id.equals(b.getId()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Expected book2 to exist"));

        BookDTO book3After = booksAfter.stream()
                .filter(b -> book3Id.equals(b.getId()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Expected book3 to exist"));

//          after replacement, book1/book2 should be unlinked
        assertThat(book1After.getAuthor()).isNull();
        assertThat(book2After.getAuthor()).isNull();

//          and book3 should now be linked to the updated author
        assertThat(book3After.getAuthor()).isNotNull();
        assertThat(book3After.getAuthor().getId()).isEqualTo(authorId);
    }

    @Test
    void getBooksByAuthorId_shouldReturnBooksForAuthor(){
//      create author
        ApiResponse<AuthorDTO> a1 = webTestClient.post()
                .uri("/api/authors")
                .bodyValue(authorCreateDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(new ParameterizedTypeReference<ApiResponse<AuthorDTO>>() {
                })
                .returnResult()
                .getResponseBody();

        assertThat(a1).isNotNull();
        assertThat(a1.getData()).isNotNull();

        AuthorDTO createdAuthor = a1.getData();
        Long authorId = createdAuthor.getId();
        assertThat(authorId).isNotNull();

        bookCreateDTO.setAuthor(AuthorSummaryDTO.builder().id(authorId).build());
        bookCreateDTO2.setAuthor(AuthorSummaryDTO.builder().id(authorId).build());

//          create books
        ApiResponse<BookDTO> book1Resp = webTestClient.post()
                .uri("/api/books")
                .bodyValue(bookCreateDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(new ParameterizedTypeReference<ApiResponse<BookDTO>>() {
                })
                .returnResult()
                .getResponseBody();

        ApiResponse<BookDTO> book2Resp = webTestClient.post()
                .uri("/api/books")
                .bodyValue(bookCreateDTO2)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(new ParameterizedTypeReference<ApiResponse<BookDTO>>() {
                })
                .returnResult()
                .getResponseBody();

        assertThat(book1Resp).isNotNull();
        assertThat(book2Resp).isNotNull();

        BookDTO book1 = book1Resp.getData();
        BookDTO book2 = book2Resp.getData();

        assertThat(book1).isNotNull();
        assertThat(book2).isNotNull();

//        Act
        ApiResponse<Set<BookSummaryDTO>> resp = webTestClient.get()
                .uri("/api/authors/{authorId}/books", authorId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<ApiResponse<Set<BookSummaryDTO>>>() {})
                .returnResult()
                .getResponseBody();

        assertThat(resp).isNotNull();
        assertThat(resp.getData()).isNotNull();

        Set<BookSummaryDTO> booksByAuthor = resp.getData();
//        Assert
        assertThat(booksByAuthor).isNotNull();
        assertThat(booksByAuthor).hasSize(2);

        assertThat(booksByAuthor)
                .extracting(
                        BookSummaryDTO::getId,
                        BookSummaryDTO::getTitle,
                        BookSummaryDTO::getPublishedDate
                )
                .containsExactlyInAnyOrder(
                        tuple(
                                book1.getId(),
                                book1.getTitle(),
                                book1.getPublishedDate()
                        ),
                        tuple(
                                book2.getId(),
                                book2.getTitle(),
                                book2.getPublishedDate()
                        )
                );
    }




    }












