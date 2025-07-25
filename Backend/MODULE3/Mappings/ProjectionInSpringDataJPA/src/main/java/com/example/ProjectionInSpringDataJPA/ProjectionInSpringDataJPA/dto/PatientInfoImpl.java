package com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.dto;



public class PatientInfoImpl {

    private final Long id;

    private final String name;

    public PatientInfoImpl(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return "PatientInfoImpl{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
