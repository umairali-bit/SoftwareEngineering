package com.umair.librarymanagement.repositories;

import com.umair.librarymanagement.LibraryManagementSystemTestConfiguration;
import com.umair.librarymanagement.entities.BookEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@Import(LibraryManagementSystemTestConfiguration.class)
@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    private BookEntity book;
    @BeforeEach
    void setUp() {
        book = new BookEntity();
        book.setTitle("Breaking Bad");
        book.setPublishedDate(LocalDate.now());
    }

    @Test
    void findByTitle() {
        bookRepository.save(book);

        Optional<BookEntity> result = bookRepository.findByTitle(book.getTitle());

        assertThat(result)
                .isPresent()
//                .isNotEmpty() - redundant after .isPresent()
                .get()
                .extracting(b-> b.getTitle())
                .isEqualTo(book.getTitle());

    }

    @Test
    void findByPublishedDateAfter() {
        bookRepository.save(book);

        BookEntity futureBook = BookEntity.builder()
                .title("Better Call Saul")
                .publishedDate(book.getPublishedDate().plusDays(1))
                .build();

        bookRepository.save(futureBook);

        List<BookEntity> booksPublishedAfter = bookRepository.findByPublishedDateAfter(book.getPublishedDate());

        assertThat(booksPublishedAfter)
        .isNotEmpty().allMatch(b -> b.getTitle().equals(futureBook.getTitle()));

    }
}