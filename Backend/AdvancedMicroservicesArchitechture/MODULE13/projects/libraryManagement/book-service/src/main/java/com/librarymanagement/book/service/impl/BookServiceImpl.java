package com.librarymanagement.book.service.impl;

import com.librarymanagement.book.dto.request.CreateBookRequest;
import com.librarymanagement.book.dto.response.BookResponse;
import com.librarymanagement.book.entity.Book;
import com.librarymanagement.book.repository.BookRepository;
import com.librarymanagement.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {


    private final BookRepository bookRepository;

    @Override
    @Transactional
    public BookResponse createBook(CreateBookRequest request) {

        if (bookRepository.existsByIsbn(request.isbnNumber())){
            throw new IllegalArgumentException(
                    "Book with this ISBN number already exists"
            );
        }

        Book book = new Book();
        book.setIsbn(request.isbnNumber());
        book.setTitle(request.title());
        book.setAuthor(request.author());
        book.setGenre(request.genre());
        book.setPublisher(request.publisher());
        book.setPublicationYear(request.publicationYear());

        book.setTotalCopies(request.totalCopies());
        book.setAvailableCopies(request.totalCopies());

        Book savedBook = bookRepository.save(book);

        return new BookResponse(

                savedBook.getId(),
                savedBook.getIsbn(),
                savedBook.getTitle(),
                savedBook.getAuthor(),
                savedBook.getGenre(),
                savedBook.getPublisher(),
                savedBook.getPublicationYear(),
                savedBook.getTotalCopies(),
                savedBook.getAvailableCopies()
        );

    }
}
