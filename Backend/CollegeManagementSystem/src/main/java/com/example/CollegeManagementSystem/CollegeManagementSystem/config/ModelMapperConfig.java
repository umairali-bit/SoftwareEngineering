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
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
