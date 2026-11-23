package com.librarymanagement.book.controller;

import com.librarymanagement.book.dto.request.CreateBookRequest;
import com.librarymanagement.book.dto.request.UpdateBookRequest;
import com.librarymanagement.book.dto.response.BookResponse;
import com.librarymanagement.book.entity.Book;
import com.librarymanagement.book.service.BookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
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
    public ResponseEntity<Page<BookResponse>> getAllBooks(
        @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "10") int size) {

        Page<BookResponse> response = bookService.getAllBooks(page, size);

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook (
            @PathVariable Long id,
            @Valid @RequestBody UpdateBookRequest request){

        BookResponse response = bookService.updateBook(id, request);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);

        return ResponseEntity.noContent().build();
    }
}

