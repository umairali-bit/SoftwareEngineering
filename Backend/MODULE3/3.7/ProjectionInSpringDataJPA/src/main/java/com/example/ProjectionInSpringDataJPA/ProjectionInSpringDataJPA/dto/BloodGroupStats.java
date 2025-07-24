package com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.dto;

import com.example.ProjectionInSpringDataJPA.ProjectionInSpringDataJPA.entity.type.BloodGroup;

public class BloodGroupStats {

    private final BloodGroup bloodGroup;
    private final Long count;


    public BloodGroupStats(BloodGroup bloodGroup, Long count) {
        this.bloodGroup = bloodGroup;
        this.count = count;
    }

    public BloodGroup getBloodGroup() {
        return bloodGroup;
    }

    public Long getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "BloodGroupStats{" +
                "bloodGroup=" + bloodGroup +
                ", count=" + count +
                '}';
    }
}
