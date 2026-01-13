package com.umair.librarymanagement.services;

import com.umair.librarymanagement.dtos.AuthorSummaryDTO;
import com.umair.librarymanagement.dtos.BookDTO;
import com.umair.librarymanagement.dtos.BookSummaryDTO;
import com.umair.librarymanagement.entities.AuthorEntity;
import com.umair.librarymanagement.entities.BookEntity;
import com.umair.librarymanagement.exception.AuthorNotFoundException;
import com.umair.librarymanagement.repositories.AuthorRepository;
import com.umair.librarymanagement.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private BookService bookService;

    private BookEntity book;
    private AuthorEntity author;

    private BookDTO bookDTO;
    private BookSummaryDTO bookSummaryDto;

    private AuthorSummaryDTO authorSummaryDto;

    @BeforeEach
    void setUp() {

        LocalDate date = LocalDate.of(2020, 1, 1);

//      Book Entity
        book = new BookEntity();
        book.setId(1L);
        book.setTitle("Breaking Bad");
        book.setPublishedDate(date);

        //      Author Entity
        author = new AuthorEntity();
        author.setId(1L);
        author.setName("Jessie Pinkman");


        authorSummaryDto = new AuthorSummaryDTO();
        authorSummaryDto.setId(author.getId());
        authorSummaryDto.setName(author.getName());



        bookDTO = new BookDTO();
        bookDTO.setAuthor(authorSummaryDto);
        bookDTO.setTitle("Breaking Bad");
        bookDTO.setPublishedDate(date);



    }

    @Test
    void testCreateBook() {

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

//      returns the exact object that your service passed into save() in this call.
        when(bookRepository.save(any(BookEntity.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        BookDTO result = bookService.createBook(bookDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Breaking Bad");
        assertThat(result.getPublishedDate()).isEqualTo(bookDTO.getPublishedDate());
        assertThat(result.getAuthor().getId()).isEqualTo(author.getId());

        verify(authorRepository).findById(author.getId());
        verify(bookRepository).save(any(BookEntity.class));

    }


    @Test
    void createBook_whenAuthorIsNull_shouldThrowAuthorNotFound() {
//      Arrange
        BookDTO dto = new BookDTO();
        dto.setTitle("Breaking Bad");
        dto.setPublishedDate(LocalDate.of(2020, 1, 1));
        dto.setAuthor(null); // triggers first condition


//      Act + Assert
        assertThatThrownBy(() -> bookService.createBook(dto))
                .isInstanceOf(AuthorNotFoundException.class);

//      No DB calls should happen
        verifyNoInteractions(authorRepository);
        verifyNoInteractions(bookRepository);
    }

    @Test
    void createBook_whenAuthorIdIsNull_shouldThrowAuthorNotFound() {
//      Arrange
        BookDTO dto = new BookDTO();
        dto.setTitle("Breaking Bad");
        dto.setPublishedDate(LocalDate.of(2020, 1, 1));

        AuthorSummaryDTO authorSummary = new AuthorSummaryDTO();
        authorSummary.setId(null); // triggers second condition
        authorSummary.setName("Jessie Pinkman");
        dto.setAuthor(authorSummary);

//      Act + Assert
        assertThatThrownBy(() -> bookService.createBook(dto))
                .isInstanceOf(AuthorNotFoundException.class);

//      No DB calls should happen
        verifyNoInteractions(authorRepository);
        verifyNoInteractions(bookRepository);
    }

    @Test
    void getAllBooks() {
    }

    @Test
    void updateBook() {
    }

    @Test
    void deleteBook() {
    }

    @Test
    void getBookByTitle() {
    }

    @Test
    void findBookPublishedAfter() {
    }
}