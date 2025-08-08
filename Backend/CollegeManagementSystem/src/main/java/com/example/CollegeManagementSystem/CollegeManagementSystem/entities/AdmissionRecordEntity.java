package com.example.CollegeManagementSystem.CollegeManagementSystem.entities;


import jakarta.persistence.*;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@Data
@Builder
@Entity
@NoArgsConstructor
@Table(name = "admission_record")
public class AdmissionRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double fees;

    @OneToOne
    @JoinColumn(name = "student_id")
    private StudentEntity student;

    private LocalDateTime admissionDate;
}