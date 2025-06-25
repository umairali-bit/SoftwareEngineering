package com.collegeManagementSystem.collegeManagementSystem.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class SubjectDTO {

    private Long id;
    private String title;
    @JsonBackReference
    private ProfessorDTO professor;
    private List<StudentDTO> students;
    private int studentCount;

    public SubjectDTO() {}

    public SubjectDTO(Long id, String title) {
        this.id = id;
        this.title = title;
    }

//    public static void main(String[] args) {
//        SubjectDTO subject = new SubjectDTO();
//        subject.setTitle("Test");
//        System.out.println(subject.getTitle());
//    }

}