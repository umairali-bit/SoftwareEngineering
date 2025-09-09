package com.example.libraryManagementSystem.LibraryManagementSystem.controller;


import com.example.libraryManagementSystem.LibraryManagementSystem.dtos.AuthorDTO;
import com.example.libraryManagementSystem.LibraryManagementSystem.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;


    //create a new AUTHOR
    @PostMapping
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody AuthorDTO authorDTO) {

        AuthorDTO created = authorService.createAuthor(authorDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);

    }


}
