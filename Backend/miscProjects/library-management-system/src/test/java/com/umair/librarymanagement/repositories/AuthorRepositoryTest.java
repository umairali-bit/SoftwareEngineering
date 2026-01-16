package com.umair.librarymanagement.repositories;

import com.umair.librarymanagement.LibraryManagementSystemTestConfiguration;
import com.umair.librarymanagement.entities.AuthorEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@Import(LibraryManagementSystemTestConfiguration.class)
@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    private AuthorEntity author;
    @BeforeEach
    void setUp() {
        author = new AuthorEntity();
        author.setName("Walter White");



    }

    @Test
    void findByNameIgnoreCase() {

        authorRepository.save(author);

        Optional<AuthorEntity> result = authorRepository.findByNameIgnoreCase(author.getName());


        assertThat(result)
                .isPresent()
//                .isNotEmpty()
                .get()
                .extracting(s-> s.getName())
                .isEqualTo(author.getName());
    }

}