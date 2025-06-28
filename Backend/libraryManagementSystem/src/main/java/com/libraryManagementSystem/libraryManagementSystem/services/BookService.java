package com.libraryManagementSystem.libraryManagementSystem.services;

import com.libraryManagementSystem.libraryManagementSystem.dto.BookDTO;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookService {

    Optional<Book> createBook(BookDTO bookdto);
    List<Book> getAllBooks();
    Optional<Book> getBookById (Long id);
    Optional<Book> findBookByTitle(String title);
    List<Book> findBookPublishedAfter(LocalDateTime dateTime);
    List<Book> findBookByAuthor(Long authorId);
    Optional<Book> updateBook (Long id, BookDTO bookdto);
    boolean deleteBookById (long id);

}
