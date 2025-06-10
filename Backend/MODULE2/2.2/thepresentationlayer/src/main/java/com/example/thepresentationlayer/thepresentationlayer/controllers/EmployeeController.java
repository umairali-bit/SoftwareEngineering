package com.example.thepresentationlayer.thepresentationlayer.controllers;


import com.example.thepresentationlayer.thepresentationlayer.dto.EmployeeDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class EmployeeController {

//    @GetMapping(path = "/getSecretMessage")
//    public String getMySuperSecretMessage() {
//        return "Secret Message: asdfal@#$DASD";
//    }

    @GetMapping(path = "/employees/{employeeId}")
    public EmployeeDTO getEmployeeById(@PathVariable Long employeeId) {
        return new EmployeeDTO(employeeId, "Umair Ali", "umairmamoor@gmail.com", 27,
                LocalDate.of(2024, 1, 2), true);

    }

    @GetMapping(path = "/employees")
        public String getAllEmployees(@RequestParam(required = false) Integer age) {

            return "Hi age" + age;


        }
}




