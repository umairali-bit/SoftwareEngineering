package com.example.CollegeManagementSystem.CollegeManagementSystem.entities;


import jakarta.persistence.*;



import jakarta.persistence.*;

@Entity
public class AdmissionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int fees;

    @OneToOne
    @JoinColumn(name = "student_id", nullable = false, unique = true)
    private Student student;

    public AdmissionRecord() {
    }

    public AdmissionRecord(Long id, int fees, Student student) {
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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "AdmissionRecord{" +
                "id=" + id +
                ", fees=" + fees +
                ", student=" + student +
                '}';
    }
}
