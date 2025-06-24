package com.collegeManagementSystem.collegeManagementSystem.services;

import com.collegeManagementSystem.collegeManagementSystem.dto.ProfessorDTO;
import com.collegeManagementSystem.collegeManagementSystem.dto.StudentDTO;
import com.collegeManagementSystem.collegeManagementSystem.dto.SubjectDTO;
import com.collegeManagementSystem.collegeManagementSystem.entities.StudentEntity;
import com.collegeManagementSystem.collegeManagementSystem.entities.SubjectEntity;
import com.collegeManagementSystem.collegeManagementSystem.repositories.SubjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class SubjectServiceImpl implements SubjectService{

    private SubjectRepository subjectRepository;
    private final ModelMapper modelMapper;

    public SubjectServiceImpl(SubjectRepository subjectRepository, ModelMapper modelMapper) {
        this.subjectRepository = subjectRepository;
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
                .map(subject -> modelMapper.map(subject,SubjectDTO.class))
                .collect(Collectors.toList());

    }

    @Override
    public SubjectDTO getSubjectById(Long id) {
        subjectExistsById(id);
        SubjectEntity subject = subjectRepository.findById(id).get();
        return modelMapper.map(subject,SubjectDTO.class);
    }

    @Override
    public SubjectDTO createSubject(SubjectDTO subjectDTO) {

        SubjectEntity subjectEntity = modelMapper.map(subjectDTO, SubjectEntity.class);
        SubjectEntity saved = subjectRepository.save(subjectEntity);

        return modelMapper.map(saved,SubjectDTO.class);
    }

    @Override
    public SubjectDTO updateSubject(Long id, SubjectDTO subjectDTO) {
        subjectExistsById(id);

        return subjectRepository.findById(id).map(subject -> {
            subject.setTitle(subjectDTO.getTitle());

            SubjectEntity updated = subjectRepository.save(subject);
            return modelMapper.map(updated,SubjectDTO.class);
        }).orElseThrow(() -> new RuntimeException());
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
