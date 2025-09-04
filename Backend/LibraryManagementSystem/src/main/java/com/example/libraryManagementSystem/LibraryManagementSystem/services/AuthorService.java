package com.example.libraryManagementSystem.LibraryManagementSystem.services;

import com.example.libraryManagementSystem.LibraryManagementSystem.dtos.AuthorDTO;
import com.example.libraryManagementSystem.LibraryManagementSystem.dtos.AuthorSummaryDTO;
import com.example.libraryManagementSystem.LibraryManagementSystem.dtos.BookDTO;
import com.example.libraryManagementSystem.LibraryManagementSystem.dtos.BookSummaryDTO;
import com.example.libraryManagementSystem.LibraryManagementSystem.entities.AuthorEntity;
import com.example.libraryManagementSystem.LibraryManagementSystem.entities.BookEntity;
import com.example.libraryManagementSystem.LibraryManagementSystem.exception.AuthorNotFoundException;
import com.example.libraryManagementSystem.LibraryManagementSystem.exception.AuthorNotFoundNameException;
import com.example.libraryManagementSystem.LibraryManagementSystem.exception.BookNotFoundException;
import com.example.libraryManagementSystem.LibraryManagementSystem.repositories.AuthorRepository;
import com.example.libraryManagementSystem.LibraryManagementSystem.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public AuthorService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public AuthorDTO createAuthor (AuthorDTO authorDTO) {
        AuthorEntity author = new AuthorEntity();

        author.setName(authorDTO.getName());

        //attach books
        Set<BookEntity> books = new HashSet<>();
        for(BookSummaryDTO bookDTO : authorDTO.getBooks()) {
            BookEntity book = bookRepository.findById(bookDTO.getId())
                    .orElseThrow(() -> new BookNotFoundException(bookDTO.getId()));

            //maintain both sides
            book.setAuthor(author);
            books.add(book);
        }

        author.setBooks(books);


        //save author
        AuthorEntity savedAuthor = authorRepository.save(author);

        return Mapper.mapToDTO(savedAuthor);

    }

    public List<AuthorDTO> getAllAuthors() {
        List<AuthorEntity> authorEntities = authorRepository.findAll();

        return authorEntities.stream()
                .map(s-> Mapper.mapToDTO(s))
                .collect(Collectors.toList());
    }

    public AuthorDTO getAuthor(Long id) {

        AuthorEntity author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));

        return Mapper.mapToDTO(author);

    }

    public boolean deleteAuthor(Long authorId) {
        AuthorEntity author = authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException(authorId));

        //Unlink book
        for (BookEntity book : new HashSet<>(author.getBooks())) {
            book.setAuthor(null);
        }
        author.getBooks().clear();

        authorRepository.delete(author);
        return true;
    }

    public AuthorDTO getAuthorByName(String name) {

        AuthorEntity author = authorRepository.findByName(name)
                .orElseThrow(() -> new AuthorNotFoundNameException(name));

        return Mapper.mapToDTO(author);

    }






}
