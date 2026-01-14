package com.umair.librarymanagement.controllers;



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

    //DELETE mapping
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {

        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/authors/by-name/{name}
    @GetMapping("/by-name/{name}")
    public ResponseEntity<AuthorDTO> getAuthorByName(@PathVariable String name) {
        AuthorDTO authorDTO = authorService.findAuthorByName(name);
        return ResponseEntity.ok(authorDTO);
    }

    // UPDATE Author
    @PutMapping("/{authorId}")
    public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable Long authorId,
                                                  @Valid @RequestBody AuthorDTO authorDTO) {
        return ResponseEntity.ok(authorService.updateAuthor(authorId, authorDTO));
    }

    // GET /api/authors/{authorId}/books
    @GetMapping("/{authorId}/books")
    public ResponseEntity<Set<BookSummaryDTO>> getBooksByAuthorId(@PathVariable Long authorId) {
        Set<BookSummaryDTO> bookSummaryDTOS = authorService.getBooksByAuthor(authorId);
        return ResponseEntity.ok(bookSummaryDTOS);
    }



}
