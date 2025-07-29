package com.example.CollegeManagementSystem.CollegeManagementSystem.entities;


import jakarta.persistence.*;


import jakarta.persistence.*;

@Entity
@Table(name = "Admission_Record")
public class AdmissionRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int fees;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id", nullable = false, unique = true)
    private StudentEntity student;

    public AdmissionRecordEntity() {}

    public AdmissionRecordEntity(Long id, int fees, StudentEntity student) {
        this.id = id;
        this.fees = fees;
        this.student = student;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getFees() {
        return fees;
    }

    public void setFees(int fees) {
        this.fees = fees;
    }

    public StudentEntity getStudent() {
        return student;
    }

    public void setStudent(StudentEntity student) {
        this.student = student;
    }


}
