package com.example.prod_ready_features.prod_ready_features.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  // This adds getters, setters, toString, equals, and hashCode
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private String title;
    private String description;
}

