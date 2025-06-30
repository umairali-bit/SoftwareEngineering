package com.libraryManagementSystem.libraryManagementSystem.dtoMapper;

import com.libraryManagementSystem.libraryManagementSystem.dto.AuthorDTO;
import com.libraryManagementSystem.libraryManagementSystem.dto.BookDTO;
import com.libraryManagementSystem.libraryManagementSystem.entities.AuthorEntity;
import com.libraryManagementSystem.libraryManagementSystem.entities.BookEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DTOMapperTest {

    @Test
    void testConvertToAuthorDTO_withBooks_noNestedAuthors() {
        // Create book entities
        BookEntity book1 = new BookEntity();
        book1.setId(2L);
        book1.setTitle("System Design");
        book1.setPublishedDate(LocalDateTime.of(2024, 1, 15, 0, 0));

        BookEntity book2 = new BookEntity();
        book2.setId(3L);
        book2.setTitle("Clean Architecture");
        book2.setPublishedDate(LocalDateTime.of(2023, 9, 1, 0, 0));

        // Create author entity and assign books
        AuthorEntity author = new AuthorEntity();
        author.setId(1L);
        author.setName("Harry Potter");
        author.getBooks().add(book1);
        author.getBooks().add(book2);

        // Convert to DTO
        AuthorDTO authorDTO = DTOMapper.convertToAuthorDTO(author);

        // Assertions
        assertEquals(1L, authorDTO.getId());
        assertEquals("Harry Potter", authorDTO.getName());

        assertNotNull(authorDTO.getBooks());
        assertEquals(2, authorDTO.getBooks().size());

        BookDTO dtoBook1 = authorDTO.getBooks().get(0);
        assertEquals(2L, dtoBook1.getId());
        assertEquals("System Design", dtoBook1.getTitle());
        assertEquals(LocalDateTime.of(2024, 1, 15, 0, 0), dtoBook1.getPublishedDate());
        assertTrue(dtoBook1.getAuthors().isEmpty(), "BookDTO authors list should be empty");

        BookDTO dtoBook2 = authorDTO.getBooks().get(1);
        assertEquals(3L, dtoBook2.getId());
        assertEquals("Clean Architecture", dtoBook2.getTitle());
        assertEquals(LocalDateTime.of(2023, 9, 1, 0, 0), dtoBook2.getPublishedDate());
        assertTrue(dtoBook2.getAuthors().isEmpty(), "BookDTO authors list should be empty");
    }
}
