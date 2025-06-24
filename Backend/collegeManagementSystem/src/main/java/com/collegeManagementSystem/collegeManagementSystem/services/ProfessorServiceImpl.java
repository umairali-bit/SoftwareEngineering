package com.collegeManagementSystem.collegeManagementSystem.services;

import com.collegeManagementSystem.collegeManagementSystem.dto.ProfessorDTO;
import com.collegeManagementSystem.collegeManagementSystem.dto.StudentDTO;
import com.collegeManagementSystem.collegeManagementSystem.dto.SubjectDTO;
import com.collegeManagementSystem.collegeManagementSystem.entities.ProfessorEntity;
import com.collegeManagementSystem.collegeManagementSystem.repositories.ProfessorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfessorServiceImpl implements ProfessorService{

    private final ProfessorRepository professorRepository;
    private final ModelMapper modelMapper;

    public ProfessorServiceImpl(ProfessorRepository professorRepository, ModelMapper modelMapper) {
        this.professorRepository = professorRepository;
        this.modelMapper = modelMapper;
    }

    public void professorExistsById(Long id) {
        if (!professorRepository.existsById(id)) {
            throw new NoSuchElementException("Professor not found by the id: " + id);
        }
    }


    @Override
    public List<ProfessorDTO> getAllProfessors() {
        List<ProfessorEntity> professors = professorRepository.findAll();
        return professors.stream()
                .map(professor -> modelMapper.map(professor, ProfessorDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProfessorDTO> getProfessorById(Long id) {
        return professorRepository.findById(id)
                .map(professorEntity -> modelMapper.map(professorEntity, ProfessorDTO.class));
    }


    @Override
    public ProfessorDTO createProfessor(ProfessorDTO inputProfessor) {

        ProfessorEntity professorEntity = modelMapper.map(inputProfessor, ProfessorEntity.class);
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
