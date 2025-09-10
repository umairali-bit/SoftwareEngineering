package com.example.libraryManagementSystem.LibraryManagementSystem.controller;


import com.example.libraryManagementSystem.LibraryManagementSystem.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;


}
