package com.librarymanagement.book.service;

import com.librarymanagement.book.dto.request.BookRequest;
import com.librarymanagement.book.dto.request.CreateBookRequest;
import com.librarymanagement.book.dto.response.BookResponse;

import java.util.List;

public interface BookService {

    BookResponse createBook (CreateBookRequest bookRequest);
    BookResponse getBookById (Long id);
    List<BookResponse> getAllBooks();

}
