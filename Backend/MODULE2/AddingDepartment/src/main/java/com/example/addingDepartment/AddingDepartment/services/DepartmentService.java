package com.example.addingDepartment.AddingDepartment.services;

import com.example.addingDepartment.AddingDepartment.dto.DepartmentDTO;
import com.example.addingDepartment.AddingDepartment.entities.DepartmentEntity;
import com.example.addingDepartment.AddingDepartment.repositories.DepartmentRepository;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;


    public DepartmentService(DepartmentRepository departmentRepository, ModelMapper modelMapper) {
        this.departmentRepository = departmentRepository;
        this.modelMapper = modelMapper;
    }

//getting departments by ID (GET/{ID})
    public Optional<DepartmentDTO> getDepartmentById(Long departmentId){
        return  departmentRepository.findById(departmentId)
                .map(departmentEntity -> modelMapper.map(departmentEntity, DepartmentDTO.class));

    }

//getting all departments (GET)
    public List<DepartmentDTO> getAllDepartments() {
        List<DepartmentEntity> departmentEntities = departmentRepository.findAll();
        return departmentEntities
                .stream()
                .map(departmentEntity -> modelMapper.map(departmentEntity, DepartmentDTO.class))
                .collect(Collectors.toList());
    }

//creating departments (POST)
public List<DepartmentDTO> createDepartments(List<DepartmentDTO> inputDepartments) {
    List<DepartmentDTO> savedDepartments = new ArrayList<>();

    for (DepartmentDTO inputDepartment : inputDepartments) {
        DepartmentEntity toSaveEntity = modelMapper.map(inputDepartment, DepartmentEntity.class);

        DepartmentEntity savedDepartment = departmentRepository.save(toSaveEntity);
        DepartmentDTO savedDTO = modelMapper.map(savedDepartment, DepartmentDTO.class);
        savedDepartments.add(savedDTO);
    }

    return savedDepartments;
}



//updating departments (PUT)
    public List<DepartmentDTO> updateDepartments(List<DepartmentDTO> departmentDTOs) {

        List<DepartmentDTO> updatedDepartments = new ArrayList<>();

        for (DepartmentDTO dto : departmentDTOs) {
            Long id = dto.getId();
            DepartmentEntity existingEntity = departmentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Department NOT found"));
            modelMapper.map(departmentDTOs, existingEntity);

            DepartmentEntity updatedEntity = departmentRepository.save(existingEntity);

            DepartmentDTO updatedDTO = modelMapper.map(updatedEntity, DepartmentDTO.class);
            updatedDepartments.add(updatedDTO);
        }
        return updatedDepartments;

    }

//deleting departments (DELETE)
    public boolean deleteAllDepartments () {
        departmentRepository.deleteAll();
        return true;
    }

}
