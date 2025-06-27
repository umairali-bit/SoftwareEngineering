package com.collegeManagementSystem.collegeManagementSystem.services;

import com.collegeManagementSystem.collegeManagementSystem.dto.ProfessorDTO;
import com.collegeManagementSystem.collegeManagementSystem.dto.StudentDTO;
import com.collegeManagementSystem.collegeManagementSystem.dto.SubjectDTO;
import com.collegeManagementSystem.collegeManagementSystem.entities.ProfessorEntity;
import com.collegeManagementSystem.collegeManagementSystem.entities.SubjectEntity;
import com.collegeManagementSystem.collegeManagementSystem.repositories.ProfessorRepository;
import com.collegeManagementSystem.collegeManagementSystem.repositories.SubjectRepository;
import com.collegeManagementSystem.collegeManagementSystem.services.SubjectService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final ProfessorRepository professorRepository;
    private final ModelMapper modelMapper;

    public SubjectServiceImpl(SubjectRepository subjectRepository,
                              ProfessorRepository professorRepository,
                              ModelMapper modelMapper) {
        this.subjectRepository = subjectRepository;
        this.professorRepository = professorRepository;
        this.modelMapper = modelMapper;
    }

    private void subjectExistsById(Long id) {
        if (!subjectRepository.existsById(id)) {
            throw new NoSuchElementException("Subject not found by id: " + id);
        }
    }

    @Override
    public List<SubjectDTO> getAllSubjects() {
        List<SubjectEntity> subjects = subjectRepository.findAll();
        return subjects.stream()
                .map(subject -> {
                    SubjectDTO dto = modelMapper.map(subject, SubjectDTO.class);
                    dto.setStudentCount(subject.getStudents() != null ? subject.getStudents().size() : 0);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SubjectDTO> getSubjectById(Long id) {
        return subjectRepository.findById(id)
                .map(subject -> {
                    SubjectDTO dto = modelMapper.map(subject, SubjectDTO.class);
                    dto.setStudentCount(subject.getStudents() != null ? subject.getStudents().size() : 0);
                    return dto;
                });
    }


    @Override
    @Transactional
    public SubjectDTO createSubject(SubjectDTO subjectDTO) {
        SubjectEntity subjectEntity = modelMapper.map(subjectDTO, SubjectEntity.class);

        if (subjectDTO.getProfessor() != null && subjectDTO.getProfessor().getId() != null) {
            ProfessorEntity professorEntity = professorRepository.findById(subjectDTO.getProfessor().getId())
                    .orElseThrow(() -> new NoSuchElementException("Professor not found by id: " + subjectDTO.getProfessor().getId()));
            subjectEntity.setProfessor(professorEntity);
        } else {
            subjectEntity.setProfessor(null);
        }

        SubjectEntity saved = subjectRepository.save(subjectEntity);

        SubjectDTO resultDTO = modelMapper.map(saved, SubjectDTO.class);
        resultDTO.setStudentCount(saved.getStudents() != null ? saved.getStudents().size() : 0);

        return resultDTO;
    }

    @Override
    @Transactional
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
            resultDTO.setStudentCount(updated.getStudents() != null ? updated.getStudents().size() : 0);

            return resultDTO;
        }).orElseThrow(() -> new NoSuchElementException("Subject not found with id: " + id));
    }

    @Override
    public List<StudentDTO> getStudentBySubjectId(Long subjectId) {
        subjectExistsById(subjectId);

        return subjectRepository.findById(subjectId)
                .map(subject -> subject.getStudents().stream()
                        .map(student -> new StudentDTO(student.getId(), student.getName()))
                        .collect(Collectors.toList()))
                .orElse(List.of());
    }

    @Override
    public List<ProfessorDTO> getProfessorBySubjectId(Long subjectId) {
        subjectExistsById(subjectId);

        return subjectRepository.findById(subjectId)
                .map(subject -> {
                    if (subject.getProfessor() == null) return List.<ProfessorDTO>of();
                    return List.of(modelMapper.map(subject.getProfessor(), ProfessorDTO.class));
                })
                .orElse(List.of());
    }

    @Override
    @Transactional
    public boolean deleteSubject(Long id) {
        subjectExistsById(id);
        subjectRepository.deleteById(id);
        return true;
    }
}
