package com.umair.librarymanagement.controllers;



import com.umair.librarymanagement.advices.ApiResponse;
import com.umair.librarymanagement.dtos.AuthorDTO;
import com.umair.librarymanagement.dtos.BookSummaryDTO;
import com.umair.librarymanagement.services.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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
    public ResponseEntity<ApiResponse<List<AuthorDTO>>> getAllAuthors() {

        List<AuthorDTO> authors = authorService.getAllAuthors();

        ApiResponse<List<AuthorDTO>> response = new ApiResponse<>();
        response.setData(authors);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AuthorDTO>> getAuthor(@PathVariable Long id) {

        AuthorDTO author = authorService.getAuthor(id);

        ApiResponse<AuthorDTO> response = new ApiResponse<>();
        response.setData(author);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {

        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-name/{name}")
    public ResponseEntity<ApiResponse<AuthorDTO>> getAuthorByName(@PathVariable String name) {

        AuthorDTO author = authorService.findAuthorByName(name);

        ApiResponse<AuthorDTO> response = new ApiResponse<>();
        response.setData(author);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{authorId}")
    public ResponseEntity<ApiResponse<AuthorDTO>> updateAuthor(
            @PathVariable Long authorId,
            @Valid @RequestBody AuthorDTO authorDTO) {

        AuthorDTO updated = authorService.updateAuthor(authorId, authorDTO);

        ApiResponse<AuthorDTO> response = new ApiResponse<>();
        response.setData(updated);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{authorId}/books")
    public ResponseEntity<ApiResponse<Set<BookSummaryDTO>>> getBooksByAuthorId(
            @PathVariable Long authorId) {

        Set<BookSummaryDTO> books = authorService.getBooksByAuthor(authorId);

        ApiResponse<Set<BookSummaryDTO>> response = new ApiResponse<>();
        response.setData(books);

        return ResponseEntity.ok(response);
    }



}
