package com.librarymanagement.book.service;

import com.librarymanagement.book.dto.request.BookRequest;
import com.librarymanagement.book.dto.request.CreateBookRequest;
import com.librarymanagement.book.dto.request.UpdateBookRequest;
import com.librarymanagement.book.dto.response.BookResponse;
import com.librarymanagement.book.entity.Book;
import org.springframework.data.domain.Page;



public interface BookService {

    BookResponse createBook (CreateBookRequest bookRequest);
    BookResponse getBookById (Long id);

//    List<BookResponse> getAllBooks();
//    Page<BookResponse> getAllBooks (int page, int size);

    Page<BookResponse> getAllBooks(int page, int size, String sortBy, String direction);
    BookResponse getBookByIsbn(String isbn);
    Page<BookResponse> getBookByTitle(String title, int page, int size, String sortBy, String direction);
    BookResponse updateBook (Long id, UpdateBookRequest request);
    void deleteBook(Long id);

}
