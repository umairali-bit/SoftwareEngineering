package com.libraryManagementSystem.libraryManagementSystem.services;

import com.libraryManagementSystem.libraryManagementSystem.dto.BookDTO;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookService {

    BookDTO createBook(BookDTO bookdto);
    List<BookDTO> getAllBooks();
    Optional<BookDTO> getBookById (Long id);
    Optional<BookDTO> findBookByTitle(String title);
    List<BookDTO> findBookPublishedAfter(LocalDateTime dateTime);
    List<BookDTO> findBookByAuthor(Long authorId);
    BookDTO updateBook (Long id, BookDTO bookdto);
    boolean deleteBookById (Long id);

}
