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

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubjectServiceImpl implements SubjectService{

    private SubjectRepository subjectRepository;
    private final ProfessorRepository professorRepository;
    private final ModelMapper modelMapper;

    public SubjectServiceImpl(SubjectRepository subjectRepository, ProfessorRepository professorRepository, ModelMapper modelMapper) {
        this.subjectRepository = subjectRepository;
        this.professorRepository = professorRepository;
        this.modelMapper = modelMapper;
    }

    public void subjectExistsById(Long id) {
        if (!subjectRepository.existsById(id)) {
            throw new NoSuchElementException("Subject not found by the id: " + id);
        }
    }

    @Override
    public List<SubjectDTO> getAllSubjects() {
        List<SubjectEntity> subjects = subjectRepository.findAll();
        return subjects.stream()
                .map(subject -> {
                    SubjectDTO dto = modelMapper.map(subject, SubjectDTO.class);
                    dto.setStudentCount(subject.getStudents().size());
                    return dto;
                })
                .collect(Collectors.toList());
    }


    @Override
    public Optional<SubjectDTO> getSubjectById(Long id) {
        Optional<SubjectEntity> subjectEntityOpt = subjectRepository.findById(id);
        return subjectEntityOpt.map(subject -> {
            SubjectDTO dto = modelMapper.map(subject, SubjectDTO.class);
            dto.setStudentCount(subject.getStudents().size());
            return dto;
        });
    }

    @Override
    public SubjectDTO createSubject(SubjectDTO subjectDTO) {
        SubjectEntity subjectEntity = modelMapper.map(subjectDTO, SubjectEntity.class);

        // Fetch professor by id (assuming subjectDTO.professor has an id)
        if (subjectDTO.getProfessor() != null && subjectDTO.getProfessor().getId() != null) {
            ProfessorEntity professorEntity = professorRepository.findById(subjectDTO.getProfessor().getId())
                    .orElseThrow(() -> new NoSuchElementException("Professor not found by id: " + subjectDTO.getProfessor().getId()));
            subjectEntity.setProfessor(professorEntity);
        } else {
            subjectEntity.setProfessor(null);
        }

        SubjectEntity saved = subjectRepository.save(subjectEntity);

        // Map back to DTO
        SubjectDTO resultDTO = modelMapper.map(saved, SubjectDTO.class);

        // Set the studentCount explicitly because it's not a direct field on SubjectEntity
        resultDTO.setStudentCount(saved.getStudents() == null? 0 : saved.getStudents().size());

        return resultDTO;
    }


    @Override
    public SubjectDTO updateSubject(Long id, SubjectDTO subjectDTO) {
        subjectExistsById(id);

        return subjectRepository.findById(id).map(subject -> {
            subject.setTitle(subjectDTO.getTitle());

            if (subjectDTO.getProfessor() != null && subjectDTO.getProfessor().getId() != null) {
                ProfessorEntity professorEntity = professorRepository.findById(subjectDTO.getProfessor().getId())
                        .orElseThrow(() -> new NoSuchElementException("Professor not found by id: " + subjectDTO.getProfessor().getId()));
                subject.setProfessor(professorEntity);
            } else {
                subject.setProfessor(null);
            }

            SubjectEntity updated = subjectRepository.save(subject);

            SubjectDTO resultDTO = modelMapper.map(updated, SubjectDTO.class);
            resultDTO.setStudentCount(updated.getStudents() == null ? 0 : updated.getStudents().size());

            return resultDTO;
        }).orElseThrow(() -> new NoSuchElementException("Subject not found with id: " + id));
    }


    @Override
    public List<StudentDTO> getStudentBySubjectId(Long subjectId) {
        subjectExistsById(subjectId);

        return subjectRepository.findById(subjectId)
                .map(subject -> subject.getStudents()
                        .stream()
                        .map(student -> new StudentDTO(student.getId(), student.getName()))
                        .toList())
                .orElse(List.of());
    }

    @Override
    public List<ProfessorDTO> getProfessorBySubjectId(Long subjectId) {
        subjectExistsById(subjectId);

        return subjectRepository.findById(subjectId)
                .map(subject -> List.of(subject.getProfessor()))
                .orElse(List.of())
                .stream()
                .map(professor -> modelMapper.map(professor, ProfessorDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    public boolean deleteSubject(Long id) {
        subjectExistsById(id);
        subjectRepository.deleteById(id);
        return true;
    }
}
