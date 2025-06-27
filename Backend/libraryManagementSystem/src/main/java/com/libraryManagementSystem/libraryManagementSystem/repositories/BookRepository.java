package com.libraryManagementSystem.libraryManagementSystem.repositories;


import com.libraryManagementSystem.libraryManagementSystem.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
