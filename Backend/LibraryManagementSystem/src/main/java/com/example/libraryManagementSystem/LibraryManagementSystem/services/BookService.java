package com.example.libraryManagementSystem.LibraryManagementSystem.services;

import com.example.libraryManagementSystem.LibraryManagementSystem.dtos.BookDTO;
import com.example.libraryManagementSystem.LibraryManagementSystem.entities.AuthorEntity;
import com.example.libraryManagementSystem.LibraryManagementSystem.entities.BookEntity;
import com.example.libraryManagementSystem.LibraryManagementSystem.exception.AuthorNotFoundException;
import com.example.libraryManagementSystem.LibraryManagementSystem.repositories.AuthorRepository;
import com.example.libraryManagementSystem.LibraryManagementSystem.repositories.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.awt.print.Book;

@Service
public class BookService {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Transactional
    public BookDTO createBook(BookDTO bookDTO) {
        BookEntity book = new BookEntity();

        // Fetch existing author instead of creating a new one
        if (bookDTO.getAuthor() == null || bookDTO.getAuthor().getId() == null) {
            throw new AuthorNotFoundException(book.getAuthor().getId());
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
}
