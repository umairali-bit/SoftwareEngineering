package com.example.libraryManagementSystem.LibraryManagementSystem.controller;


import com.example.libraryManagementSystem.LibraryManagementSystem.dtos.AuthorDTO;
import com.example.libraryManagementSystem.LibraryManagementSystem.services.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;


    //create a new AUTHOR
    @PostMapping
    public ResponseEntity<AuthorDTO> createAuthor(@Valid @RequestBody AuthorDTO authorDTO) {

        AuthorDTO created = authorService.createAuthor(authorDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);

    }

    //GET all authors
    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {

      //  List<AuthorDTO> getAll = authorService.getAllAuthors();
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    //GET /api/authors/{id}
    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthor(@PathVariable Long id) {

        //AuthorDTO author = authorService.getAuthorById(id);
        return ResponseEntity.ok(authorService.getAuthor(id));
    }

}
