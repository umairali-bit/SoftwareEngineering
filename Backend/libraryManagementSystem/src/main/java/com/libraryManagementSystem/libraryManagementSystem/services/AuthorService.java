package com.libraryManagementSystem.libraryManagementSystem.services;

import com.libraryManagementSystem.libraryManagementSystem.dto.AuthorDTO;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    AuthorDTO createAuthor (AuthorDTO authorDTO);
    List<AuthorDTO> getAllAuthors();
    Optional<AuthorDTO> getAuthorById(Long id);
    Optional<AuthorDTO> findAuthorByName (String name);
    AuthorDTO updateAuthor (Long id, AuthorDTO authorDTO);
    boolean deleteAuthorById (Long id);


}
