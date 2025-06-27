package com.collegeManagementSystem.collegeManagementSystem.services;

import com.collegeManagementSystem.collegeManagementSystem.dto.AdmissionRecordDTO;
import com.collegeManagementSystem.collegeManagementSystem.entities.AdmissionRecordEntity;
import com.collegeManagementSystem.collegeManagementSystem.entities.StudentEntity;
import com.collegeManagementSystem.collegeManagementSystem.repositories.AdmissionRecordRepository;
import com.collegeManagementSystem.collegeManagementSystem.repositories.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class AdmissionRecordImpl implements AdmissionRecordService{

    private final AdmissionRecordRepository admissionRecordRepository;
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    public AdmissionRecordImpl(AdmissionRecordRepository admissionRecordRepository, StudentRepository studentRepository,
                               ModelMapper modelMapper) {
        this.admissionRecordRepository = admissionRecordRepository;
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<AdmissionRecordDTO> getAllAdmissions() {
       List<AdmissionRecordEntity> admissions = admissionRecordRepository.findAll();
       return admissions.stream()
               .map(admission -> modelMapper.map(admission, AdmissionRecordDTO.class))
               .collect(Collectors.toList());
    }

    @Override
    public AdmissionRecordDTO getAdmissionById(Long id) {
        AdmissionRecordEntity admission = admissionRecordRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Admission record not found with id: " + id));
        return modelMapper.map(admission,AdmissionRecordDTO.class);

    }

    @Override
    public AdmissionRecordDTO createAdmissionRecord(AdmissionRecordDTO dto) {
        Long studentId = dto.getStudent().getId();

        if (admissionRecordRepository.findByStudent_id(studentId).isPresent()) {
            throw new IllegalStateException("Admission already exists for student ID: " + studentId);
        }

        AdmissionRecordEntity entity = modelMapper.map(dto, AdmissionRecordEntity.class);

        StudentEntity studentEntity = studentRepository.findById(studentId)
                .orElseThrow(() -> new NoSuchElementException("Student not found with id: " + studentId));

        entity.setStudent(studentEntity);
        if (entity.getAdmissionDateTime() == null) {
            entity.setAdmissionDateTime(LocalDateTime.now());
        }

        AdmissionRecordEntity saved = admissionRecordRepository.save(entity);
        return modelMapper.map(saved, AdmissionRecordDTO.class);
    }

    @Override
    public AdmissionRecordDTO updateAdmission(Long id, AdmissionRecordDTO admissionRecordDTO) {
        AdmissionRecordEntity record = admissionRecordRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Admission record not found with id: " + + id));
        record.setFees(admissionRecordDTO.getFees());

        AdmissionRecordEntity updated = admissionRecordRepository.save(record);

        return modelMapper.map(updated, AdmissionRecordDTO.class);
    }

    @Override
    public AdmissionRecordDTO getAdmissionRecordByStudentId(Long studentId) {
        AdmissionRecordEntity record = admissionRecordRepository.findByStudent_id(studentId)
                .orElseThrow(() -> new NoSuchElementException("No admission record found for student ID: " + studentId));
        return modelMapper.map(record, AdmissionRecordDTO.class);
    }

    @Override
    public boolean deleteAdmissionRecord (Long id){
        if (!admissionRecordRepository.existsById(id)) {
            throw new NoSuchElementException("Admission record not found with id: " + id);
        }
        admissionRecordRepository.deleteById(id);
        return true;
    }
}
