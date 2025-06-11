package com.example.thepresentationlayer.thepresentationlayer.controllers;


import com.example.thepresentationlayer.thepresentationlayer.dto.EmployeeDTO;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

//    @GetMapping(path = "/getSecretMessage")
//    public String getMySuperSecretMessage() {
//        return "Secret Message: asdfal@#$DASD";
//    }

    @GetMapping(path = "/{employeeId}")
    public EmployeeDTO getEmployeeById(@PathVariable Long employeeId) {
        return new EmployeeDTO(employeeId, "Umair Ali", "umairmamoor@gmail.com", 27,
                LocalDate.of(2024, 1, 2), true);

    }

    @GetMapping
        public String getAllEmployees(Integer age) {

            return "Hi age " + age;


        }
}




