package com.example.CollegeManagementSystem.CollegeManagementSystem.config;


import com.example.CollegeManagementSystem.CollegeManagementSystem.dtos.SubjectDTO;
import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.SubjectEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Skip id when mapping SubjectDTO to SubjectEntity (to avoid overwriting primary key)
        modelMapper.typeMap(SubjectDTO.class, SubjectEntity.class).addMappings(mapper -> {
            mapper.skip(SubjectEntity::setId);
            mapper.skip(SubjectEntity::setStudents); // skip students to handle manually in service
        });

        // Map SubjectEntity to SubjectDTO but handle students manually or with a converter if needed
        modelMapper.typeMap(SubjectEntity.class, SubjectDTO.class).addMappings(mapper -> {
            mapper.skip(SubjectDTO::setStudents); // skip students, map manually later
        });

        return modelMapper;
        }

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
