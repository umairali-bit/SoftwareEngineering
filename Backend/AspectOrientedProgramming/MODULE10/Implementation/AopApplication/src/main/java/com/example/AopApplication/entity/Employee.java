package com.example.AopApplication.entity;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Employee {


    @NotBlank(message = "name cannot be blank")
    private String name;

    @Email(message = "invalid email")
    private String email;

    @Min(value = 18, message = "Age must be at least 18")
    private int age;


}
