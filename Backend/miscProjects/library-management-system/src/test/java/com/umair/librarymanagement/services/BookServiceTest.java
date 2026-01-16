package com.umair.librarymanagement.services;

import com.umair.librarymanagement.dtos.AuthorDTO;
import com.umair.librarymanagement.dtos.AuthorSummaryDTO;
import com.umair.librarymanagement.dtos.BookDTO;
import com.umair.librarymanagement.dtos.BookSummaryDTO;
import com.umair.librarymanagement.entities.AuthorEntity;
import com.umair.librarymanagement.entities.BookEntity;
import com.umair.librarymanagement.exception.AuthorNotFoundException;
import com.umair.librarymanagement.repositories.AuthorRepository;
import com.umair.librarymanagement.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private BookService bookService;

    private BookEntity book, book2;
    private AuthorEntity author, author2;
    private BookDTO bookDTO, bookDTO2;
    private List<AuthorSummaryDTO> authorSummaryDto;


    @BeforeEach
    void setUp() {

        LocalDate date = LocalDate.of(2020, 1, 1);
        LocalDate date2 = LocalDate.of(2020, 5, 5);

//      Author Entity
        author = new AuthorEntity();
        author.setId(1L);
        author.setName("Jessie Pinkman");


//      Book Entity
        book = new BookEntity();
        book.setId(1L);
        book.setTitle("Breaking Bad");
        book.setPublishedDate(date);

//      linking both sides
        book.setAuthor(author);
        author.getBooks().add(book);


//      Author Entity
        author2 = new AuthorEntity();
        author2.setId(2L);
        author2.setName("Walter White");
        author2.getBooks().add(book2);


//      Book Entity
        book2 = new BookEntity();
        book2.setId(2L);
        book2.setTitle("Better Call Saul");
        book2.setPublishedDate(date2);
        book2.setAuthor(author2);


//      Author summary for dto

        authorSummaryDto = new ArrayList<>(List.of(new AuthorSummaryDTO(), new AuthorSummaryDTO()));
        authorSummaryDto.get(0).setId(author.getId());
        authorSummaryDto.get(0).setName(author.getName());
        authorSummaryDto.get(1).setId(author2.getId());
        authorSummaryDto.get(1).setName(author2.getName());


//      Book dto
        bookDTO = new BookDTO();
        bookDTO.setAuthor(authorSummaryDto.get(0));
        bookDTO.setTitle("Breaking Bad");
        bookDTO.setPublishedDate(date);

        bookDTO2 = new BookDTO();
        bookDTO2.setAuthor(authorSummaryDto.get(1));
        bookDTO2.setTitle("Better Call Saul");
        bookDTO2.setPublishedDate(date2);

    }

    @Test
    void testCreateBook() {

        when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));

//      returns the exact object that your service passed into save() in this call.
        when(bookRepository.save(any(BookEntity.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        BookDTO result = bookService.createBook(bookDTO);

//      Assert
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Breaking Bad");
        assertThat(result.getPublishedDate()).isEqualTo(bookDTO.getPublishedDate());
        assertThat(result.getAuthor().getId()).isEqualTo(author.getId());

        verify(authorRepository).findById(author.getId());
        verify(bookRepository).save(any(BookEntity.class));

    }


    @Test
    void createBook_whenAuthorIsNull_shouldThrowAuthorNotFound() {
//      Arrange
        BookDTO dto = new BookDTO();
        dto.setTitle("Breaking Bad");
        dto.setPublishedDate(LocalDate.of(2020, 1, 1));
        dto.setAuthor(null); // triggers first condition


//      Act + Assert
        assertThatThrownBy(() -> bookService.createBook(dto))
                .isInstanceOf(AuthorNotFoundException.class);

//      No DB calls should happen
        verifyNoInteractions(authorRepository);
        verifyNoInteractions(bookRepository);
    }

    @Test
    void createBook_whenAuthorIdIsNull_shouldThrowAuthorNotFound() {
//      Arrange
        BookDTO dto = new BookDTO();
        dto.setTitle("Breaking Bad");
        dto.setPublishedDate(LocalDate.of(2020, 1, 1));

        AuthorSummaryDTO authorSummary = new AuthorSummaryDTO();
        authorSummary.setId(null); // triggers second condition
        authorSummary.setName("Jessie Pinkman");
        dto.setAuthor(authorSummary);

//      Act + Assert
        assertThatThrownBy(() -> bookService.createBook(dto))
                .isInstanceOf(AuthorNotFoundException.class);

//      No DB calls should happen
        verifyNoInteractions(authorRepository);
        verifyNoInteractions(bookRepository);
    }

    @Test
    void getAllBooks() {

//      Important: repository returns in desired order already
        when(bookRepository.findAll(Sort.by(Sort.Direction.DESC, "publishedDate")))
                .thenReturn(List.of(book2, book));
//      Act
        List<BookDTO> result = bookService.getAllBooks();

//      Assert: repo called with correct sort
        verify(bookRepository).findAll(Sort.by(Sort.Direction.DESC, "publishedDate"));

//      Assert: mapping + order
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getId()).isEqualTo(2L);
        assertThat(result.get(0).getTitle()).isEqualTo("Better Call Saul");
        assertThat(result.get(0).getPublishedDate())
                .isEqualTo(book2.getPublishedDate());
        assertThat(result.get(0).getAuthor().getId()).isEqualTo(2L);

        assertThat(result.get(1).getId()).isEqualTo(1L);
        assertThat(result.get(1).getTitle()).isEqualTo("Breaking Bad");
        assertThat(result.get(1).getPublishedDate()).isEqualTo(LocalDate.of(2020, 1, 1));
        assertThat(result.get(1).getAuthor().getId()).isEqualTo(1L);
    }

    @Test
    void updateBook_shouldUpdateFields_andMoveBookToNewAuthor() {

//         Arrange: existing book is linked to an old author, and request updates fields + author
        Long bookId = 100L;

        AuthorEntity existingAuthor = new AuthorEntity();
        existingAuthor.setId(1L);
        existingAuthor.setName("Old Author");

        AuthorEntity newAuthor = new AuthorEntity();
        newAuthor.setId(2L);
        newAuthor.setName("New Author");

        BookEntity book = new BookEntity();
        book.setId(bookId);
        book.setTitle("Old Book");
        book.setPublishedDate(LocalDate.of(2020, 1, 1));
        book.setAuthor(existingAuthor);

        existingAuthor.getBooks().add(book);

        BookDTO updateBook = new BookDTO();
        updateBook.setTitle("New Book");
        updateBook.setPublishedDate(LocalDate.of(2020, 5, 5));

        AuthorSummaryDTO authorSummaryDTO = new AuthorSummaryDTO();
        authorSummaryDTO.setId(2L);
        authorSummaryDTO.setName("New Author");
        updateBook.setAuthor(authorSummaryDTO);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(authorRepository.findById(2L)).thenReturn(Optional.of(newAuthor));
        when(bookRepository.save(same(book))).thenAnswer(inv -> inv.getArgument(0));

//         Act: call service method under test
        BookDTO result = bookService.updateBook(bookId, updateBook);

//         Assert: fields updated + book moved from old author to new author (bidirectional)
        assertThat(book.getTitle()).isEqualTo("New Book");
        assertThat(book.getPublishedDate()).isEqualTo(LocalDate.of(2020, 5, 5));
        assertThat(existingAuthor.getBooks()).doesNotContain(book);
        assertThat(book.getAuthor()).isSameAs(newAuthor);
        assertThat(newAuthor.getBooks()).contains(book);

//         Verify: correct repository interactions happened
        verify(bookRepository).findById(bookId);
        verify(authorRepository).findById(2L);
        verify(bookRepository).save(book);

        assertThat(result).isNotNull();
    }

    @Test
    void updateBook_shouldChangeAuthor_andUnlinkFromOldAuthor() {

//         Arrange: DTO author is null so author-update block is skipped
        Long bookId = 100L;

        AuthorEntity existingAuthor = new AuthorEntity();
        existingAuthor.setId(1L);
        existingAuthor.setName("Old Author");

        BookEntity book = new BookEntity();
        book.setId(bookId);
        book.setTitle("Old Book");
        book.setPublishedDate(LocalDate.of(2020, 1, 1));
        book.setAuthor(existingAuthor);

        existingAuthor.getBooks().add(book);

        BookDTO updateBook = new BookDTO();
        updateBook.setTitle("New Book");
        updateBook.setPublishedDate(LocalDate.of(2020, 5, 5));
        updateBook.setAuthor(null); // outer IF is FALSE

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookRepository.save(same(book))).thenAnswer(inv -> inv.getArgument(0));

//         Act: call service method under test
        bookService.updateBook(bookId, updateBook);

//         Assert: author stays the same (no unlinking / no reassignment)
        assertThat(book.getAuthor()).isSameAs(existingAuthor);
        assertThat(existingAuthor.getBooks()).contains(book);

//         Verify: authorRepository should not be called at all
        verifyNoInteractions(authorRepository);
    }

    @Test
    void updateBook_whenBookHasNoOldAuthor_shouldNotUnlinkButShouldAssignNewAuthor() {

//         Arrange: book has no current author, but DTO provides a new author (inner IF false, outer IF true)
        Long bookId = 100L;

        AuthorEntity newAuthor = new AuthorEntity();
        newAuthor.setId(2L);
        newAuthor.setName("New Author");

        BookEntity book = new BookEntity();
        book.setId(bookId);
        book.setTitle("Old Book");
        book.setPublishedDate(LocalDate.of(2020, 1, 1));
        book.setAuthor(null); // inner IF is FALSE

        BookDTO updateBook = new BookDTO();
        updateBook.setTitle("New Book");
        updateBook.setPublishedDate(LocalDate.of(2020, 5, 5));

        AuthorSummaryDTO authorSummaryDTO = new AuthorSummaryDTO();
        authorSummaryDTO.setId(2L);
        authorSummaryDTO.setName("New Author");
        updateBook.setAuthor(authorSummaryDTO); // outer IF is TRUE

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(authorRepository.findById(2L)).thenReturn(Optional.of(newAuthor));
        when(bookRepository.save(any(BookEntity.class))).thenAnswer(inv -> inv.getArgument(0));

//         Act: call service method under test
        bookService.updateBook(bookId, updateBook);

//         Assert: book gets assigned to new author (no unlinking needed since there was no old author)
        assertThat(book.getAuthor()).isSameAs(newAuthor);
        assertThat(newAuthor.getBooks()).contains(book);

//         Verify: new author was looked up
        verify(authorRepository).findById(2L);
    }



    @Test
    void deleteBook_shouldUnlinkAuthorAndDeleteBook() {

//      Arrange
        assertThat(book.getAuthor()).isSameAs(author);
        assertThat(author.getBooks()).contains(book);

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
//      Act
        bookService.deleteBook(book.getId());
//      Assert: repo interactions
        verify(bookRepository).findById(book.getId());
        verify(bookRepository).delete(book);

//      Assert linking happened
        assertThat(book.getAuthor()).isNull();
        assertThat(author.getBooks()).doesNotContain(book); //book is a set<>


    }

    @Test
    void deleteBook_whenBookHasNoAuthor_shouldJustDelete(){
//      Arrange
        book.setAuthor(null);//author is null
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

//        Act
        bookService.deleteBook(book.getId());

//        Assert
        assertThat(book.getAuthor()).isNull();
        verify(bookRepository).delete(book);

    }

    @Test
    void getBookByTitle() {

        when(bookRepository.findByTitle(book.getTitle())).thenReturn(Optional.of(book));

        BookDTO result = bookService.getBookByTitle(book.getTitle());

        assertThat(result)
                .isNotNull();

        verify(bookRepository).findByTitle(book.getTitle());
        verifyNoMoreInteractions(bookRepository);
        verifyNoInteractions(authorRepository);

    }

    @Test
    void findBookPublishedAfter() {

        LocalDate cutOff = book.getPublishedDate();

        when(bookRepository.findByPublishedDateAfter(cutOff))
                .thenReturn(List.of(book, book2));


//        ACT
        List<BookDTO> result = bookService.findBookPublishedAfter(cutOff);

//        Assert: repo called correctly
        verify(bookRepository).findByPublishedDateAfter(cutOff);

//        Assert: mapping + data
        assertThat(result).hasSize(2);

        assertThat(result.get(0).getTitle()).isEqualTo(book.getTitle());
        assertThat(result.get(0).getId()).isEqualTo(book.getId());
        assertThat(result.get(0).getPublishedDate()).isEqualTo(book.getPublishedDate());
        assertThat(result.get(0).getAuthor()).isEqualTo(bookDTO.getAuthor());

        assertThat(result.get(1).getTitle()).isEqualTo(book2.getTitle());
        assertThat(result.get(1).getId()).isEqualTo(book2.getId());
        assertThat(result.get(1).getPublishedDate()).isEqualTo(book2.getPublishedDate());
        assertThat(result.get(1).getAuthor()).isEqualTo(bookDTO2.getAuthor());





    }
}