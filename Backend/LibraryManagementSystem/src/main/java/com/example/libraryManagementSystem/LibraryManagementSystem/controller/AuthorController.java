package com.example.libraryManagementSystem.LibraryManagementSystem.controller;


import com.example.libraryManagementSystem.LibraryManagementSystem.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;


}
