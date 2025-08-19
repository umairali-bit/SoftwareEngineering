package com.example.CollegeManagementSystem.CollegeManagementSystem.repositories;

import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.ProfessorEntity;
import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;


public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {

    @Query("""
  select distinct s
  from SubjectEntity s
  left join fetch s.professor
  left join fetch s.students
  where s.id in :ids
""")
    Set<SubjectEntity> findWithProfessorAndStudentsById(@Param("ids") Set<Long> ids);
}
