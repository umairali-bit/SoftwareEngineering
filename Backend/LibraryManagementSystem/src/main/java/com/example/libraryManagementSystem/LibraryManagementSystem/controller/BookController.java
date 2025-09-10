package com.example.libraryManagementSystem.LibraryManagementSystem.controller;


import com.example.libraryManagementSystem.LibraryManagementSystem.dtos.BookDTO;
import com.example.libraryManagementSystem.LibraryManagementSystem.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
