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
    public EmployeeDTO getEmployeeById(@PathVariable(name = "employeeId") Long id) {
        return new EmployeeDTO(id, "Umair Ali", "umairmamoor@gmail.com", 27,
                LocalDate.of(2024, 1, 2), true);

    }

    @GetMapping
        public String getAllEmployees(@RequestParam Integer age) {

            return "Hi age " + age;
    }

    @PostMapping (path = "/greeting")
    public String testingPost() {
        return "Hello from Post";
    }

    @PostMapping(path = "/createJava")
    public EmployeeDTO createNewEmployeeJ(@RequestBody EmployeeDTO inputEmployee) {

        return new EmployeeDTO(100L,"Anuj Sharma","Dummy@gmail",27,LocalDate.now(),true);
//        inputEmployee.setId(100L);
//        return inputEmployee;
    }

    @PostMapping(path = "/createPostman")
    public EmployeeDTO createNewEmployeeP(@RequestBody EmployeeDTO inputEmployee) {
        inputEmployee.setId(100L);
        return inputEmployee;
    }

    @PutMapping String testingPut() {
        return "Hello from Put";
    }
}




