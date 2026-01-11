package com.umair.librarymanagement.services;

import com.umair.librarymanagement.dtos.AuthorDTO;
import com.umair.librarymanagement.dtos.BookSummaryDTO;
import com.umair.librarymanagement.entities.AuthorEntity;
import com.umair.librarymanagement.entities.BookEntity;
import com.umair.librarymanagement.repositories.AuthorRepository;
import com.umair.librarymanagement.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private AuthorService authorService;

    private AuthorEntity mockAuthorEntity;
    private AuthorDTO mockAuthorDto;

    private BookEntity mockBookEntity;
    private BookSummaryDTO mockBookSummaryDTO;

    @BeforeEach
    void setUp() {

//      Book Entity
        mockBookEntity = new BookEntity();
        mockBookEntity.setId(1L);
        mockBookEntity.setTitle("Breaking Bad");
        mockBookEntity.setPublishedDate(LocalDate.now());


//      Author Entity
        mockAuthorEntity = new AuthorEntity();
        mockAuthorEntity.setId(1L);
        mockAuthorEntity.setName("Jessie Pinkman");


//      DTO coming from API
        mockBookSummaryDTO = new BookSummaryDTO();
        mockBookSummaryDTO.setId(1L);
        mockBookSummaryDTO.setTitle("Breaking Bad");
        mockBookSummaryDTO.setPublishedDate(mockBookEntity.getPublishedDate());

        mockAuthorDto = new AuthorDTO();
        mockAuthorDto.setName("Jessie Pinkman");
        mockAuthorDto.getBooks().add(mockBookSummaryDTO);


    }


    @Test
    void testCreateAuthor() {

//     Stub the BookRepository to simulate an existing book in the system.
//     When the service looks up a book by ID, return the mockBookEntity.
       when(bookRepository.findById(anyLong())).thenReturn(Optional.of(mockBookEntity));

//     Stub the AuthorRepository save call.
//     When the service looks up a book by ID, return the mockBookEntity.
        when(authorRepository.save(any(AuthorEntity.class))).thenReturn(mockAuthorEntity);

//     Call the service method under test.
//      This should:
        // 1. Fetch the book by ID
        // 2. Attach the book to the new author
        // 3. Save the author
        // 4. Return an AuthorDTO

        AuthorDTO authorDto = authorService.createAuthor(mockAuthorDto);

        assertThat(authorDto).isNotNull();
//      Verify the author's name in the response matches the saved author
        assertThat(authorDto.getName()).isEqualTo(mockAuthorEntity.getName());

//      Capture the AuthorEntity passed to the save() method
        ArgumentCaptor<AuthorEntity> captor = ArgumentCaptor.forClass(AuthorEntity.class);
        verify(authorRepository).save(captor.capture());
        verify(bookRepository).findById(anyLong());
        AuthorEntity capturedAuthor = captor.getValue();
        assertThat(capturedAuthor.getName()).isEqualTo(mockAuthorEntity.getName());

//      verify books attached + owning side set
        assertThat(capturedAuthor.getBooks().size()).isEqualTo(1);
        assertThat(capturedAuthor.getBooks().iterator().next().getId()).isEqualTo(mockBookEntity.getId());

    }

    @Test
    void getAllAuthors() {
    }

    @Test
    void getAuthor() {
    }

    @Test
    void deleteAuthor() {
    }

    @Test
    void findAuthorByName() {
    }

    @Test
    void updateAuthor() {
    }

    @Test
    void getBooksByAuthor() {
    }
}