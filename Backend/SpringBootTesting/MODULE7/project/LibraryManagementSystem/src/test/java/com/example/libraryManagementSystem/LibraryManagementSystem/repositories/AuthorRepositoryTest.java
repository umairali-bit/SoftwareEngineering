package com.example.libraryManagementSystem.LibraryManagementSystem.repositories;


import com.example.libraryManagementSystem.LibraryManagementSystem.TestContainerConfiguration;
import com.example.libraryManagementSystem.LibraryManagementSystem.entities.AuthorEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Import(TestContainerConfiguration.class)
@DataJpaTest
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;


    @Test
    void findByNameIgnoreCase() {
        // given
        AuthorEntity author = new AuthorEntity();
        author.setName("J. K. Rowling");

        authorRepository.save(author);

        // when
        Optional<AuthorEntity> result =
                authorRepository.findByNameIgnoreCase("j. k. rowling");

        // then
        assertTrue(result.isPresent());
        assertEquals("J. K. Rowling", result.get().getName());
    }

}



