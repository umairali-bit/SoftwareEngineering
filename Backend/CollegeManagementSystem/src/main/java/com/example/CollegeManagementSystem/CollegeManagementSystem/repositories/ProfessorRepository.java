package com.example.CollegeManagementSystem.CollegeManagementSystem.repositories;

import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.ProfessorEntity;
import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfessorRepository extends JpaRepository<ProfessorEntity, Long> {

    @Query("select p from ProfessorEntity p left join fetch p.students where p.id = :id")
    Optional<ProfessorEntity> findWithStudentsById(@Param("id") Long id);

}
