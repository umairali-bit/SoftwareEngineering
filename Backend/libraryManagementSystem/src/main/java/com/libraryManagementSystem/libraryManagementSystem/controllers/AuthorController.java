package com.libraryManagementSystem.libraryManagementSystem.controllers;


import com.libraryManagementSystem.libraryManagementSystem.dto.AuthorDTO;
import com.libraryManagementSystem.libraryManagementSystem.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;


    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById (@PathVariable  Long id) {
        return authorService.getAuthorById(id)
                .map(author -> ResponseEntity.ok(author))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<AuthorDTO> findAuthorByName (@RequestParam String name) {
        return authorService.findAuthorByName(name)
                .map(author -> ResponseEntity.ok(author))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> createAuthor (@RequestBody AuthorDTO authorDTO) {
        AuthorDTO created = authorService.createAuthor(authorDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> updateAuthor (@PathVariable Long id,
                                                   @RequestBody AuthorDTO authorDTO) {
        AuthorDTO updated = authorService.updateAuthor(id,authorDTO);
        return ResponseEntity.ok(updated);
    }


}
