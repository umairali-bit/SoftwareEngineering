package com.librarymanagement.book.controller;

import com.librarymanagement.book.dto.request.CreateBookRequest;
import com.librarymanagement.book.dto.response.BookResponse;
import com.librarymanagement.book.service.BookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")

public class BookController {

    private final BookService bookService;



    @PostMapping
    public ResponseEntity<BookResponse> createBook (@Valid
                                                    @RequestBody CreateBookRequest createBookRequest) {

        BookResponse response = bookService.createBook(createBookRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
}

