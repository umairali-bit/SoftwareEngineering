package com.example.addingDepartment.AddingDepartment.services;

import com.example.addingDepartment.AddingDepartment.dto.DepartmentDTO;
import com.example.addingDepartment.AddingDepartment.dto.EmployeeDTO;
import com.example.addingDepartment.AddingDepartment.entities.DepartmentEntity;
import com.example.addingDepartment.AddingDepartment.repositories.DepartmentRepository;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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


    public Optional<DepartmentDTO> getDepartmentById(Long departmentId){
        return  departmentRepository.findById(departmentId)
                .map(departmentEntity -> modelMapper.map(departmentEntity, DepartmentDTO.class));

    }

    public List<DepartmentDTO> getAllDepartments() {
        List<DepartmentEntity> departmentEntities = departmentRepository.findAll();
        return departmentEntities
                .stream()
                .map(departmentEntity -> modelMapper.map(departmentEntity, DepartmentDTO.class))
                .collect(Collectors.toList());
    }

    public DepartmentDTO createDepartment(DepartmentDTO inputDepartment) {
        DepartmentEntity toSaveEntity = modelMapper.map(inputDepartment, DepartmentEntity.class);
        DepartmentEntity savedDepartment = departmentRepository.save(toSaveEntity);

        return modelMapper.map(savedDepartment, DepartmentDTO.class);
    }

    public DepartmentDTO updateDepartment(Long departmentId, DepartmentDTO departmentDTO) {

        DepartmentEntity existingEntity = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department NOT found"));

        modelMapper.map(departmentDTO, existingEntity);

        DepartmentEntity updatedEntity = departmentRepository.save(existingEntity);

        return modelMapper.map(updatedEntity, DepartmentDTO.class);

    }





}
