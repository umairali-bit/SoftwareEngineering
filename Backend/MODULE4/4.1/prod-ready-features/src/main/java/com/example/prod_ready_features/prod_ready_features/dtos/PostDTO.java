package com.example.prod_ready_features.prod_ready_features.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private String title;
    private String description;
}
