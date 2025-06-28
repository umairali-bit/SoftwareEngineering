package com.libraryManagementSystem.libraryManagementSystem.services;

import static com.libraryManagementSystem.libraryManagementSystem.dtoMapper.DTOMapper.convertToBookDTO;
import com.libraryManagementSystem.libraryManagementSystem.dto.AuthorDTO;
import com.libraryManagementSystem.libraryManagementSystem.dto.BookDTO;
import com.libraryManagementSystem.libraryManagementSystem.dtoMapper.DTOMapper;
import com.libraryManagementSystem.libraryManagementSystem.entities.AuthorEntity;
import com.libraryManagementSystem.libraryManagementSystem.entities.BookEntity;
import com.libraryManagementSystem.libraryManagementSystem.repositories.AuthorRepository;
import com.libraryManagementSystem.libraryManagementSystem.repositories.BookRepository;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService{


    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;



    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;

    }


    @Override
    public BookDTO createBook(BookDTO bookdto) {
        //create a BookEntity
        BookEntity bookEntity = new BookEntity();
        bookEntity.setTitle(bookdto.getTitle());


        if (bookdto.getPublishedDate() != null && bookdto.getPublishedDate().isBefore(LocalDateTime.now())){
            bookEntity.setPublishedDate(bookdto.getPublishedDate());
        } else {
            throw new IllegalArgumentException("Published date must be in the past");

        }

        List<AuthorEntity> authorEntities = new ArrayList<>();
        for(AuthorDTO authorDTO : bookdto.getAuthors()) {
            AuthorEntity author = authorRepository.findById(authorDTO.getId())
                    .orElseThrow(() -> new NoSuchElementException("Author not found with id: " + authorDTO.getId()));
            authorEntities.add(author);
        }
        bookEntity.setAuthors(authorEntities);

        //Save Book
        BookEntity savedBook = bookRepository.save(bookEntity);

        return convertToBookDTO(savedBook);
    }
/* ModelMapper version
    @Override
    public BookDTO createBook(BookDTO bookdto) {
        // Validate publishedDate before mapping
        if (bookdto.getPublishedDate() == null || !bookdto.getPublishedDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Published date must be in the past");
        }

        // Map BookDTO to BookEntity (basic fields, excluding authors for now)
        BookEntity bookEntity = modelMapper.map(bookdto, BookEntity.class);

        // Override publishedDate after validation (optional if included in mapping)
        bookEntity.setPublishedDate(bookdto.getPublishedDate());

        // Fetch author entities from DB based on DTO author IDs
        List<AuthorEntity> authorEntities = new ArrayList<>();
        for (AuthorDTO authorDTO : bookdto.getAuthors()) {
            AuthorEntity author = authorRepository.findById(authorDTO.getId())
                    .orElseThrow(() -> new NoSuchElementException("Author not found with id: " + authorDTO.getId()));
            authorEntities.add(author);
        }

        bookEntity.setAuthors(authorEntities);

        // Save BookEntity
        BookEntity savedBook = bookRepository.save(bookEntity);

        // Map saved BookEntity back to BookDTO (including authors)
        BookDTO savedDTO = modelMapper.map(savedBook, BookDTO.class);

        return savedDTO;
    }

 */

    @Override
    public List<BookDTO> getAllBooks() {
        List<BookEntity> books = bookRepository.findAll();
        return books.stream()
                .map(book -> convertToBookDTO(book))
                .collect(Collectors.toList());
    }

    /*
    modelMapperVersion
    @Override
    public List<BookDTO> getAllBooks() {
     List<BookEntity> books = bookRepository.findAll();
       return books.stream()
            .map(book -> modelMapper.map(book, BookDTO.class))
            .collect(Collectors.toList());
}
     */

    @Override
    public Optional<BookDTO> getBookById(Long id) {
        return bookRepository.findById(id)
                .map(bookEntity -> convertToBookDTO(bookEntity));
    }
    /*
    modelMapper version
    @Override
    public Optional<BookDTO> getBookById(Long id) {
     return bookRepository.findById(id)
            .map(bookEntity -> modelMapper.map(bookEntity, BookDTO.class));
    }
     */



    @Override
    public Optional<BookDTO> findBookByTitle(String title) {
        return bookRepository.findBookByTitleIgnoreCase(title)
                .map(bookEntity -> convertToBookDTO(bookEntity));
    }

    /*
    @Override
    public Optional<BookDTO> findBookByTitle(String title) {
    return bookRepository.findBookByTitleIgnoreCase(title)
            .map(bookEntity -> modelMapper.map(bookEntity, BookDTO.class));
}
     */


    @Override
    public List<BookDTO> findBookPublishedAfter(LocalDateTime dateTime) {
        List<BookEntity> books = bookRepository.findBooksPublishedAfter(dateTime);
        if (books.isEmpty()) {
            throw new NoSuchElementException("No books found published after: " + dateTime);
        }
        return books.stream()
                .map(bookEntity -> convertToBookDTO(bookEntity))
                .collect(Collectors.toList());
    }

    /*
    @Override
    public List<BookDTO> findBookPublishedAfter(LocalDateTime dateTime) {
    List<BookEntity> books = bookRepository.findBooksPublishedAfter(dateTime);

    if (books.isEmpty()) {
        throw new NoSuchElementException("No books found published after: " + dateTime);
    }

    return books.stream()
            .map(bookEntity -> modelMapper.map(bookEntity, BookDTO.class))
            .collect(Collectors.toList());
}

     */

    @Override
    public List<BookDTO> findBookByAuthor(Long authorId) {
        if(!authorRepository.existsById(authorId)) {
            throw new NoSuchElementException("Author not found with id: " + authorId);
        }

        List<BookEntity> books = bookRepository.findBooksByAuthorsId(authorId);
        return books.stream()
                .map(bookEntity -> convertToBookDTO(bookEntity))
                .collect(Collectors.toList());
    }
    /*

@Override
public List<BookDTO> findBookByAuthor(Long authorId) {
    if (!authorRepository.existsById(authorId)) {
        throw new NoSuchElementException("Author not found with id: " + authorId);
    }

    List<BookEntity> books = bookRepository.findBooksByAuthorsId(authorId);

    return books.stream()
            .map(bookEntity -> modelMapper.map(bookEntity, BookDTO.class))
            .collect(Collectors.toList());
}
     */

    @Override
    public BookDTO updateBook(Long id, BookDTO bookdto) {

        return bookRepository.findById(id)
                .map(bookEntity -> {
                    bookEntity.setTitle(bookdto.getTitle());

                    if (bookdto.getPublishedDate() != null && bookdto.getPublishedDate().isBefore(LocalDateTime.now())) {

                    } else {
                        throw new IllegalArgumentException("Published date must be in the past");
                    }

                    List<AuthorEntity> authorEntities = new ArrayList<>();
                    for (AuthorDTO authorDTO : bookdto.getAuthors()) {
                        AuthorEntity author = authorRepository.findById(authorDTO.getId())
                                .orElseThrow(() -> new NoSuchElementException("Author not found with id: " +
                                        authorDTO.getId()));
                        authorEntities.add(author);
                    }
                    bookEntity.setAuthors(authorEntities);

                    BookEntity updatedBook = bookRepository.save(bookEntity);

                    return convertToBookDTO(updatedBook);
                }).orElseThrow(() -> new NoSuchElementException("Book not found with id: " + id));


    }

    /*
    @Override
   public BookDTO updateBook(Long id, BookDTO bookdto) {
    return bookRepository.findById(id)
        .map(existingBook -> {
            // Map DTO fields to existing entity, ignoring authors and publishedDate first
            modelMapper.map(bookdto, existingBook);

            // Validate publishedDate separately
            if (bookdto.getPublishedDate() != null && bookdto.getPublishedDate().isBefore(LocalDateTime.now())) {
                existingBook.setPublishedDate(bookdto.getPublishedDate());
            } else {
                throw new IllegalArgumentException("Published date must be in the past");
            }

            // Handle authors separately: fetch entities from DB
            List<AuthorEntity> authorEntities = new ArrayList<>();
            for (AuthorDTO authorDTO : bookdto.getAuthors()) {
                AuthorEntity author = authorRepository.findById(authorDTO.getId())
                    .orElseThrow(() -> new NoSuchElementException("Author not found with id: " + authorDTO.getId()));
                authorEntities.add(author);
            }
            existingBook.setAuthors(authorEntities);

            // Save updated entity
            BookEntity updatedBook = bookRepository.save(existingBook);

            // Map back to DTO and return
            return modelMapper.map(updatedBook, BookDTO.class);
        })
        .orElseThrow(() -> new NoSuchElementException("Book not found with id: " + id));
}
     */

    @Override
    public boolean deleteBookById(Long id) {
        return false;
    }
}
