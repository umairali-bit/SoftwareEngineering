package com.libraryManagementSystem.libraryManagementSystem.services;


import com.libraryManagementSystem.libraryManagementSystem.dto.AuthorDTO;
import com.libraryManagementSystem.libraryManagementSystem.dto.BookDTO;
import com.libraryManagementSystem.libraryManagementSystem.entities.AuthorEntity;
import com.libraryManagementSystem.libraryManagementSystem.entities.BookEntity;
import com.libraryManagementSystem.libraryManagementSystem.repositories.AuthorRepository;
import com.libraryManagementSystem.libraryManagementSystem.repositories.BookRepository;
import static com.libraryManagementSystem.libraryManagementSystem.dtoMapper.DTOMapper.convertToAuthorDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements  AuthorService{

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }


    @Override
    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setName(authorDTO.getName());

        List<BookEntity> bookEntities = new ArrayList<>();
        if (authorDTO.getBooks() != null) {
            for (BookDTO bookDTO : authorDTO.getBooks()) {
                BookEntity book = bookRepository.findById(bookDTO.getId())
                        .orElseThrow(() -> new NoSuchElementException("Book not found with id: " + bookDTO.getId()));
                bookEntities.add(book);
            }
        }

        authorEntity.setBooks(bookEntities);

        AuthorEntity savedAuthor = authorRepository.save(authorEntity);
        return convertToAuthorDTO(savedAuthor);
    }


    @Override
    public List<AuthorDTO> getAllAuthors() {
        List<AuthorEntity> authors = authorRepository.findAll();
        return authors.stream()
                .map(author -> convertToAuthorDTO(author))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AuthorDTO> getAuthorById(Long id) {
        return authorRepository.findById(id)
                .map(authorEntity -> convertToAuthorDTO(authorEntity));
    }

    @Override
    public Optional<AuthorDTO> findAuthorByName(String name) {

        return authorRepository.findAuthorByName(name)
                .map(authorEntity -> convertToAuthorDTO(authorEntity));
    }

    @Override
    public AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO) {
        AuthorEntity authorEntity = authorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Author NOT found with id: " + id));

        authorEntity.setName(authorDTO.getName());

        AuthorEntity updatedAuthor = authorRepository.save(authorEntity);

        return convertToAuthorDTO(updatedAuthor);
    }

    @Override
    public boolean deleteAuthorById(Long id) {
        return false;
    }
}
