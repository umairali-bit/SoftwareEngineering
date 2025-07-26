package com.exceptionHandelingInSpringBoot.services;


import com.exceptionHandelingInSpringBoot.dto.EmployeeDTO;
import com.exceptionHandelingInSpringBoot.entities.EmployeeEntity;
import com.exceptionHandelingInSpringBoot.exceptions.ResourceNotFound;
import com.exceptionHandelingInSpringBoot.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;


import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public void existsByEmployeeId(Long employeeId) {
        boolean exist = employeeRepository.existsById(employeeId);
        if(!exist) throw new ResourceNotFound("Employee not found by the id: " + employeeId);
    }


    public Optional<EmployeeDTO> getEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class));
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        return employeeEntities
                .stream()
                .map(employeeEntity ->  modelMapper.map(employeeEntity, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    public EmployeeDTO createEmployee(EmployeeDTO inputEmployee) {
        EmployeeEntity toSaveEntity = modelMapper.map(inputEmployee, EmployeeEntity.class);
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(toSaveEntity);

        return modelMapper.map(savedEmployeeEntity, EmployeeDTO.class);
    }


    public EmployeeDTO updateEmployeeById(Long employeeId, EmployeeDTO employeeDTO) {
       existsByEmployeeId(employeeId);
        EmployeeEntity existingEntity = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException(
                "Employee not found"));

        existingEntity.setName(employeeDTO.getName());
        existingEntity.setEmail(employeeDTO.getEmail());
        existingEntity.setAge(employeeDTO.getAge());
        existingEntity.setDateOfJoining(employeeDTO.getDateOfJoining());
        existingEntity.setIsActive(employeeDTO.getIsActive());

        EmployeeEntity updatedEntity = employeeRepository.save(existingEntity);
        return modelMapper.map(updatedEntity, EmployeeDTO.class);


    }

    public boolean deleteEmployeeById(Long employeeId) {
        existsByEmployeeId(employeeId);
        employeeRepository.deleteById(employeeId);
        return true;


    }

    public EmployeeDTO updatePartialEmployeeById(Long employeeId, Map<String, Object> updates) {
       existsByEmployeeId(employeeId);
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException(
                "Employee not found"));

        updates.forEach((field, value) -> {
            Field fieldToUpdate = ReflectionUtils.findField(EmployeeEntity.class, field);
            fieldToUpdate.setAccessible(true);
            ReflectionUtils.setField(fieldToUpdate, employeeEntity, value);
        });

        return modelMapper.map(employeeRepository.save(employeeEntity), EmployeeDTO.class);








    }
}