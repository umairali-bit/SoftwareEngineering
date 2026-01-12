package com.umair.librarymanagement.services;


import com.umair.librarymanagement.dtos.AuthorDTO;
import com.umair.librarymanagement.dtos.BookSummaryDTO;
import com.umair.librarymanagement.entities.AuthorEntity;
import com.umair.librarymanagement.entities.BookEntity;
import com.umair.librarymanagement.exception.AuthorNotFoundException;
import com.umair.librarymanagement.exception.AuthorNotFoundNameException;
import com.umair.librarymanagement.exception.BookNotFoundException;
import com.umair.librarymanagement.repositories.AuthorRepository;
import com.umair.librarymanagement.repositories.BookRepository;
import jakarta.transaction.Transactional;
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

    @Transactional
    public void deleteAuthor(Long authorId) {
        AuthorEntity author = authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException(authorId));

        //Unlink Book (both sides)
        for (BookEntity book : new HashSet<>(author.getBooks())) {
            book.setAuthor(null);
        }
        author.getBooks().clear();

        authorRepository.delete(author);

    }

    public AuthorDTO findAuthorByName(String name) {

        AuthorEntity author = authorRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new AuthorNotFoundNameException(name));

        return Mapper.mapToDTO(author);

    }

    @Transactional
    public AuthorDTO updateAuthor(Long authorId, AuthorDTO authorDTO) {
        // 1. Find the existing author
        AuthorEntity author = authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException(authorId));

        // 2. Update simple fields
        author.setName(authorDTO.getName()); //dto -> entity

        // 3. Update books
        if(authorDTO.getBooks() != null) { //if null. only updates name, does NOT touch books
            // Clear old associations
            author.getBooks().forEach(bookEntity -> bookEntity.setAuthor(null));
            author.getBooks().clear();

            //attach new books
            Set<BookEntity> updatedBooks = authorDTO.getBooks().stream()
                    .map(bookSummaryDTO -> bookRepository.findById(bookSummaryDTO.getId())
                            .orElseThrow(() -> new BookNotFoundException(bookSummaryDTO.getId())))
                    .peek(bookEntity -> bookEntity.setAuthor(author))
                    .collect(Collectors.toSet());

            author.setBooks(updatedBooks);// DTO book IDs -> fetch BookEntity -> attach to author (dto->entity + relationship)

        }
        // 4. Save the updated author
        AuthorEntity savedAuthor = authorRepository.save(author);

        // 5. Convert back to DTO
        return Mapper.mapToDTO(savedAuthor);//entity->dto
    }

    public Set<BookSummaryDTO> getBooksByAuthor(Long authorId) {
        AuthorEntity author = authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException(authorId));

        return author.getBooks().stream()
                .map(bookEntity -> new BookSummaryDTO(
                        bookEntity.getId(),
                        bookEntity.getTitle(),
                        bookEntity.getPublishedDate()
                ))
                .collect(Collectors.toSet());
    }










}
