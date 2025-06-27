package com.libraryManagementSystem.libraryManagementSystem.repositories;


import com.libraryManagementSystem.libraryManagementSystem.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

}
