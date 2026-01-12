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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.awt.print.Book;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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

//      attach both sides (needed for deleteAuthor logic)
        mockBookEntity.setAuthor(mockAuthorEntity);
        mockAuthorEntity.getBooks().add(mockBookEntity);


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
    void createAuthor_whenBookNotFound_shouldThrowAndNotSaveAuthor() {
//      Arrange
        Long missingBookId = 1L;

        BookSummaryDTO missingBookSummaryDTO = new BookSummaryDTO();
        missingBookSummaryDTO.setId(missingBookId);

        AuthorDTO dto = new AuthorDTO();
        dto.setName("Walter White");
        dto.getBooks().add(missingBookSummaryDTO);

        when(bookRepository.findById(missingBookId)).thenReturn(Optional.empty());
//      Act + Assert
        assertThatThrownBy(() -> authorService.createAuthor(dto)).isInstanceOf(BookNotFoundException.class);

//      Verify: author save should never happen
        verify(authorRepository,never()).save(any(AuthorEntity.class));

//      Verify: book lookup was attempted
        verify(bookRepository).findById(missingBookId);
    }

    @Test
    void getAllAuthors() {

//    creating an additional author
        AuthorEntity authorEntity2 = new AuthorEntity();
        authorEntity2.setId(2L);
        authorEntity2.setName("Walter White");

        when(authorRepository.findAll()).thenReturn(List.of(authorEntity2, mockAuthorEntity));

        List<AuthorDTO> result = authorService.getAllAuthors();

        assertThat(result)
                .isNotNull()
                .hasSize(2);

        assertThat(result)
                .extracting(authorDTOS-> authorDTOS.getName())
                .containsExactly("Walter White", "Jessie Pinkman");
    }

    @Test
    void testGetAuthorById_ThrowsException() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authorService.getAuthor(1L)).isInstanceOf(AuthorNotFoundException.class);

        verify(authorRepository).findById(1L);
    }

    @Test
    void getAuthor() {

        Long id  = mockAuthorEntity.getId();

        when(authorRepository.findById(id)).thenReturn(Optional.of(mockAuthorEntity));

        AuthorDTO authorDto = authorService.getAuthor(1L);

        assertThat(authorDto).isNotNull();
        assertThat(authorDto.getId()).isEqualTo(mockAuthorEntity.getId());

        verify(authorRepository).findById(id);

    }

    @Test
    void deleteAuthor_shouldUnlinkBooksAndDeleteAuthor() {

        when(authorRepository.findById(1L)).thenReturn(Optional.of(mockAuthorEntity));

        authorService.deleteAuthor(1L);

//      Act + Assert
        verify(authorRepository).findById(1L);
        verify(authorRepository).delete(mockAuthorEntity);

        assertThat(mockBookEntity.getAuthor()).isNull();
        assertThat(mockAuthorEntity.getBooks()).isEmpty();

    }

    @Test
    void deleteAuthor_whenAuthorNotFound_shouldThrowAndNotSaveAuthor() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());

//      Act + Assert
        assertThatThrownBy(() -> authorService.deleteAuthor(1L)).isInstanceOf(AuthorNotFoundException.class);
        verify(authorRepository).findById(1L);
        verify(authorRepository, never()).delete(any());

//      deleteById() must also NOT be called
        verify(authorRepository, never()).deleteById(anyLong());

//      BookRepository must not be touched at all
        verifyNoInteractions(bookRepository);
    }


    @Test
    void findAuthorByName() {
//      Assign
        when(authorRepository.findByNameIgnoreCase(anyString())).thenReturn(Optional.of(mockAuthorEntity));

        AuthorDTO authorDto = authorService.findAuthorByName("Walter White");

//      Act + Assert
        assertThat(authorDto).isNotNull();
        assertThat(authorDto.getName()).isEqualTo(mockAuthorEntity.getName());

        verify(authorRepository).findByNameIgnoreCase("Walter White");
        verifyNoMoreInteractions(authorRepository);

    }

    @Test
    void findAuthorByName_whenNotFound_shouldThrow() {
        when(authorRepository.findByNameIgnoreCase(anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authorService.findAuthorByName("Walter White"))
                .isInstanceOf(AuthorNotFoundNameException.class)
                .hasMessage("Author not found with the name: Walter White");

        verify(authorRepository).findByNameIgnoreCase("Walter White");
    }

    @Test
    void updateAuthor() {
    }

    @Test
    void getBooksByAuthor() {
    }
}