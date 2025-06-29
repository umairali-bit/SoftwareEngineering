package com.libraryManagementSystem.libraryManagementSystem.controllers;


import com.libraryManagementSystem.libraryManagementSystem.dto.BookDTO;
import com.libraryManagementSystem.libraryManagementSystem.entities.BookEntity;
import com.libraryManagementSystem.libraryManagementSystem.services.BookService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;


    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id)
                .map(book -> ResponseEntity.ok(book))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<BookDTO> findBookByTitle(@RequestParam String title) {
        return bookService.findBookByTitle(title)
                .map(book -> ResponseEntity.ok(book))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/published-after")
    public ResponseEntity<List<BookDTO>> getBooksPublishedAfter(
            @RequestParam("dateTime")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime) {
        List<BookDTO> books = bookService.findBookPublishedAfter(dateTime);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{authorId}")
        public ResponseEntity<List<BookDTO>> getBooksByAuthor(@PathVariable("authorId") Long authorId) {

        List<BookDTO> books = bookService.findBookByAuthor(authorId);
        return ResponseEntity.ok(books);

        }



    @PostMapping
    public ResponseEntity<BookDTO> createBook (@RequestBody BookDTO bookDTO) {
        BookDTO created = bookService.createBook(bookDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity <BookDTO> updateBook (@PathVariable Long id,
                                              @RequestBody BookDTO bookDTO) {

        BookDTO updated = bookService.updateBook(id,bookDTO);
        return ResponseEntity.ok(updated);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteBook (@PathVariable Long id) {
        boolean deleted = bookService.deleteBookById(id);
        if (deleted) return ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();

    }










}
