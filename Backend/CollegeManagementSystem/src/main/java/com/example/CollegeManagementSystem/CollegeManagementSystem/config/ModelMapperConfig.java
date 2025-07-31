package com.example.CollegeManagementSystem.CollegeManagementSystem.config;


import com.example.CollegeManagementSystem.CollegeManagementSystem.dtos.SubjectDTO;
import com.example.CollegeManagementSystem.CollegeManagementSystem.entities.SubjectEntity;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper mapper = new ModelMapper();

        mapper.typeMap(SubjectEntity.class, SubjectDTO.class).addMappings(m -> {
            m.map(src -> src.getProfessor().getId(), (dest, v) -> dest.setProfessorId((Long) v));
            m.map(src -> src.getProfessor().getName(), (dest,v) -> dest.setProfessorName((String) v));

            m.<Set<Long>> map(
                    src -> {
                        if(src.getStudents() == null) return null;
                        return src.getStudents().stream()
                                .map(s->s.getId())
                                .collect(Collectors.toSet());

                    },
                    (dest, v) -> dest.setStudents((Set<Long>) v)
            );
        });
        return mapper;
    }
}
