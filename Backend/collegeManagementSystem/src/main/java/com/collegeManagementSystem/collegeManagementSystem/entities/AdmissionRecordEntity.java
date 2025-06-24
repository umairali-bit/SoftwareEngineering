package com.collegeManagementSystem.collegeManagementSystem.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "student_record")
public class AdmissionRecordEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer fees;

    @OneToOne
    @JoinColumn(name = "student_id", unique = true)
    private StudentEntity student;
}
