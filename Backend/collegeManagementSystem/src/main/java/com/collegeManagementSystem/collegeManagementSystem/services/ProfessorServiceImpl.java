package com.collegeManagementSystem.collegeManagementSystem.services;

import com.collegeManagementSystem.collegeManagementSystem.dto.ProfessorDTO;
import com.collegeManagementSystem.collegeManagementSystem.dto.StudentDTO;
import com.collegeManagementSystem.collegeManagementSystem.dto.SubjectDTO;
import com.collegeManagementSystem.collegeManagementSystem.entities.ProfessorEntity;
import com.collegeManagementSystem.collegeManagementSystem.entities.SubjectEntity;
import com.collegeManagementSystem.collegeManagementSystem.repositories.ProfessorRepository;
import com.collegeManagementSystem.collegeManagementSystem.repositories.SubjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfessorServiceImpl implements ProfessorService{

    private final ProfessorRepository professorRepository;
    private final SubjectRepository subjectRepository;
    private final ModelMapper modelMapper;

    public ProfessorServiceImpl(ProfessorRepository professorRepository, SubjectRepository subjectRepository, ModelMapper modelMapper) {
        this.professorRepository = professorRepository;
        this.subjectRepository = subjectRepository;
        this.modelMapper = modelMapper;
    }

    public void professorExistsById(Long id) {
        if (!professorRepository.existsById(id)) {
            throw new NoSuchElementException("Professor not found by the id: " + id);
        }
    }


    @Override
    @Transactional(readOnly = true)
    public List<ProfessorDTO> getAllProfessors() {
        List<ProfessorEntity> professors = professorRepository.findAll();

        return professors.stream().map(professor -> {
            ProfessorDTO dto = new ProfessorDTO();
            dto.setId(professor.getId());
            dto.setName(professor.getName());
            dto.setTitle(professor.getTitle());

            // Map subjects with studentCount and professor info
            List<SubjectDTO> subjectDTOs = professor.getSubjects().stream().map(subject -> {
                SubjectDTO subjectDTO = new SubjectDTO();
                subjectDTO.setId(subject.getId());
                subjectDTO.setTitle(subject.getTitle());
                subjectDTO.setStudentCount(subject.getStudents().size());

                // Set minimal professor info to avoid recursion
                subjectDTO.setProfessor(new ProfessorDTO(
                        professor.getId(),
                        professor.getTitle(),
                        professor.getName()
                ));

                return subjectDTO;
            }).collect(Collectors.toList());
            dto.setSubjects(subjectDTOs);

            // Map students (students directly linked to the professor)
            List<StudentDTO> studentDTOs = professor.getStudents().stream().map(student -> {
                return new StudentDTO(student.getId(), student.getName());
            }).collect(Collectors.toList());
            dto.setStudents(studentDTOs);

            return dto;
        }).collect(Collectors.toList());
    }


    @Override
    public Optional<ProfessorDTO> getProfessorById(Long id) {
        return professorRepository.findById(id)
                .map(professorEntity -> modelMapper.map(professorEntity, ProfessorDTO.class));
    }


    @Override
    public ProfessorDTO createProfessor(ProfessorDTO inputProfessor) {
        ProfessorEntity professorEntity = new ProfessorEntity();
        professorEntity.setName(inputProfessor.getName());
        professorEntity.setTitle(inputProfessor.getTitle());

        // Subjects are optional
        if (inputProfessor.getSubjects() != null) {
            for (SubjectDTO subjectDTO : inputProfessor.getSubjects()) {
                SubjectEntity subjectEntity = new SubjectEntity();
                subjectEntity.setTitle(subjectDTO.getTitle());
                subjectEntity.setProfessor(professorEntity); // IMPORTANT: set owning side
                professorEntity.getSubjects().add(subjectEntity);
            }
        }

        ProfessorEntity saved = professorRepository.save(professorEntity);
        return modelMapper.map(saved, ProfessorDTO.class);
    }


//efficient way
    @Override
    public ProfessorDTO updateProfessor(Long id, ProfessorDTO professorDTO) {
        ProfessorEntity professor = professorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Professor not found by id: " + id));

        professor.setTitle(professorDTO.getTitle());
        ProfessorEntity updated = professorRepository.save(professor);
        return modelMapper.map(updated, ProfessorDTO.class);
    }



    @Override
    public List<StudentDTO> getStudentsByProfessorId(Long professorId) {
        professorExistsById(professorId);
        return professorRepository.findById(professorId)
                .map(professor -> professor.getStudents()
                        .stream()
                        .map(student -> new StudentDTO(student.getId(), student.getName()))
                        .toList())

                        .orElse(List.of());
    }

    @Override
    public List<SubjectDTO> getSubjectsByProfessorId(Long professorId) {
        professorExistsById(professorId);
        ProfessorEntity professor = professorRepository.findById(professorId).get();
        return professor.getSubjects().stream()
                .map(subject -> new SubjectDTO(subject.getId(), subject.getTitle()))
                .toList();
    }

    @Override
    public boolean deleteProfessor(Long id) {
        professorExistsById(id);
        professorRepository.deleteById(id);
        return true;

    }


}
