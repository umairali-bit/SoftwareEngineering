package com.librarymanagement.book.controller;

import com.librarymanagement.book.dto.request.CreateBookRequest;
import com.librarymanagement.book.dto.response.BookResponse;
import com.librarymanagement.book.service.BookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById (@PathVariable Long id) {

        BookResponse response = bookService.getBookById(id);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks() {

        List<BookResponse> response = bookService.getAllBooks();
        
        return ResponseEntity.ok().body(response);
    }
}

