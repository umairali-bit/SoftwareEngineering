package com.librarymanagement.book.service;

import com.librarymanagement.book.dto.request.BookRequest;
import com.librarymanagement.book.dto.request.CreateBookRequest;
import com.librarymanagement.book.dto.response.BookResponse;

public interface BookService {

    BookResponse createBook (CreateBookRequest bookRequest);

}
