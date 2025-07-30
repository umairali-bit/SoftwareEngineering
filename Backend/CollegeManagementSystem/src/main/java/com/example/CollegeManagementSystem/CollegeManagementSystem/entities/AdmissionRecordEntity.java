package com.example.CollegeManagementSystem.CollegeManagementSystem.entities;


import jakarta.persistence.*;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@Entity
@NoArgsConstructor
@Table(name = "admission_record")
public class AdmissionRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double fees;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    private StudentEntity student;
}