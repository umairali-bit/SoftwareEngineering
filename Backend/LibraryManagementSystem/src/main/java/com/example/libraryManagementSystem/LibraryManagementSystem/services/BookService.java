package com.example.libraryManagementSystem.LibraryManagementSystem.services;

import com.example.libraryManagementSystem.LibraryManagementSystem.annotations.AdminOnly;
import com.example.libraryManagementSystem.LibraryManagementSystem.dtos.BookDTO;
import com.example.libraryManagementSystem.LibraryManagementSystem.entities.AuthorEntity;
import com.example.libraryManagementSystem.LibraryManagementSystem.entities.BookEntity;
import com.example.libraryManagementSystem.LibraryManagementSystem.exception.AuthorNotFoundException;
import com.example.libraryManagementSystem.LibraryManagementSystem.exception.BookNotFoundException;
import com.example.libraryManagementSystem.LibraryManagementSystem.repositories.AuthorRepository;
import com.example.libraryManagementSystem.LibraryManagementSystem.repositories.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Transactional
    public BookDTO createBook(BookDTO bookDTO) {
        BookEntity book = new BookEntity();

        // Fetch existing author instead of creating a new one
        if (bookDTO.getAuthor() == null || bookDTO.getAuthor().getId() == null) {
            throw new AuthorNotFoundException();
        }

        AuthorEntity author = authorRepository.findById(bookDTO.getAuthor().getId())
                               .orElseThrow(() -> new AuthorNotFoundException(bookDTO.getAuthor().getId()));


        book.setTitle(bookDTO.getTitle());
        book.setPublishedDate(bookDTO.getPublishedDate());
        book.setAuthor(author);
        author.getBooks().add(book);//maintaining bidirectional


        BookEntity savedBook = bookRepository.save(book);

        return Mapper.mapToDTO(savedBook);
    }

    public List<BookDTO> getAllBooks() {
        List<BookEntity> bookEntities = bookRepository.findAll(Sort.by(Sort.Direction.DESC, "publishedDate"));
        return bookEntities.stream()
                .map(bookEntity -> Mapper.mapToDTO(bookEntity))
                .collect(Collectors.toList());
    }


    @Transactional
    @AdminOnly
    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        //1. Find the existing book
        BookEntity book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        //2. update simple fields
        book.setTitle(bookDTO.getTitle());
        book.setPublishedDate(bookDTO.getPublishedDate());


        //3. Update author
        if (bookDTO.getAuthor() != null) {
            if (book.getAuthor() != null) {
                book.getAuthor().getBooks().remove(book);
                book.setAuthor(null);

            }

            // find the new author
            AuthorEntity newAuthor = authorRepository.findById(bookDTO.getAuthor().getId())
                    .orElseThrow(() -> new AuthorNotFoundException(bookDTO.getAuthor().getId()));

            //assign the new author
            book.setAuthor(newAuthor);

            //maintain bidirectional
            newAuthor.getBooks().add(book);
        }

        //4. Save the updated book
        BookEntity savedBook = bookRepository.save(book);

        //5. convert back to DTO
        return Mapper.mapToDTO(savedBook);


    }

    @Transactional
    @AdminOnly
    public boolean deleteBook(Long id) {
        BookEntity book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        //Unlink Author (both sides)
        if(book.getAuthor() != null) {
            book.getAuthor().getBooks().remove(book);
            book.setAuthor(null);
        }

        bookRepository.delete(book);
        return true;
    }

    public BookDTO getBookByTitle(String title) {

        BookEntity book = bookRepository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("Book Not Found" + title));

        return Mapper.mapToDTO(book);


    }

    public List<BookDTO> findBookPublishedAfter(LocalDateTime date) {
        return bookRepository.findByPublishedDateAfter(date).stream()
                .map(bookEntity -> Mapper.mapToDTO(bookEntity))
                .collect(Collectors.toList());
    }




}
