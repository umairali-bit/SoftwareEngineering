package com.collegeManagementSystem.collegeManagementSystem.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "student_record")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdmissionRecordEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer fees;

    @OneToOne
    @JoinColumn(name = "student_id", unique = true)
    private StudentEntity student;

    private LocalDateTime admissionDateTime = LocalDateTime.now();
}
