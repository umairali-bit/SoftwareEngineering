package com.example.libraryManagementSystem.LibraryManagementSystem.services;

import com.example.libraryManagementSystem.LibraryManagementSystem.dtos.AuthorDTO;
import com.example.libraryManagementSystem.LibraryManagementSystem.dtos.AuthorSummaryDTO;
import com.example.libraryManagementSystem.LibraryManagementSystem.dtos.BookDTO;
import com.example.libraryManagementSystem.LibraryManagementSystem.entities.AuthorEntity;
import com.example.libraryManagementSystem.LibraryManagementSystem.entities.BookEntity;
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
        for(BookDTO bookDTO : authorDTO.getBooks()) {
            BookEntity book = bookRepository.findById(bookDTO.getId())
                    .orElseThrow(() -> new BookNotFoundException(bookDTO.getId()));

            //maintain both sides
            book.setAuthor(author);
            books.add(book);
        }

        author.setBooks(books);


        //save author
        AuthorEntity savedAuthor = authorRepository.save(author);

        //convert back to dto
        AuthorDTO savedAuthorDTO = new AuthorDTO();
        savedAuthorDTO.setId(savedAuthorDTO.getId());
        savedAuthorDTO.setName(savedAuthor.getName());
        savedAuthorDTO.setBooks(savedAuthorDTO.getBooks());

        return savedAuthorDTO;

    }

    public List<AuthorDTO> getAllAuthors() {
        List<AuthorEntity> authorEntities = authorRepository.findAll();

        return authorEntities.stream()
                .map(authorEntity -> {
                    AuthorDTO authorDTO = new AuthorDTO();
                    authorDTO.setId(authorEntity.getId());
                    authorDTO.setName(authorEntity.getName());

                    Set<BookDTO> bookDTOSet = authorEntity.getBooks().stream()
                            .map(bookDTO -> new BookDTO(
                                    bookDTO.getId(),
                                    bookDTO.getTitle(),
                                    bookDTO.getPublishedDate(),
                                    new AuthorSummaryDTO(authorEntity.getId(), authorEntity.getName())
                            ))
                            .collect(Collectors.toSet());

                    authorDTO.setBooks(bookDTOSet);

                    return  authorDTO;


                })
                .collect(Collectors.toList());
    }


}
