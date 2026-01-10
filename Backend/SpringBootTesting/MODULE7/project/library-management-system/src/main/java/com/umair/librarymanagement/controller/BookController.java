package com.umair.librarymanagement.controller;



import com.umair.librarymanagement.dtos.BookDTO;
import com.umair.librarymanagement.services.BookService;
import jakarta.validation.constraints.PastOrPresent;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    //create a new book
    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {

        BookDTO newBook = bookService.createBook(bookDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBook);
    }

    //GET allBooks
    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return  ResponseEntity.status(HttpStatus.OK).body(bookService.getAllBooks());
    }

    //UPDATE
    @PutMapping("/{bookId}")
    private ResponseEntity<BookDTO> updateBook(@PathVariable Long bookId,
                                               @RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.updateBook(bookId, bookDTO));
    }

    //DELETE mapping
    @DeleteMapping("/{bookId}")
    public ResponseEntity<Boolean> deleteBook(@PathVariable Long bookId) {
        return ResponseEntity.ok(bookService.deleteBook(bookId));
    }

    // GET /api/book/by-title/{title}
    @GetMapping("/by-title/{title}")
    public ResponseEntity<BookDTO>  getBooksByTitle(@PathVariable String title) {
        return ResponseEntity.ok(bookService.getBookByTitle(title));
    }

    // GET /api/books/published-after?date=2000-01-01
    @GetMapping("/published-after")
    public ResponseEntity<List<BookDTO>> getBooksPublishedAfter(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @PastOrPresent LocalDate date) {

        // convert LocalDate to LocalDateTime (start of day)
        List<BookDTO> books = bookService.findBookPublishedAfter(date);
        return ResponseEntity.ok(books);
    }

    }




