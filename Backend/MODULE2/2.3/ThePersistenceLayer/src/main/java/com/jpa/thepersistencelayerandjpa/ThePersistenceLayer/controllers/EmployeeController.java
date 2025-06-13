package com.jpa.thepersistencelayerandjpa.ThePersistenceLayer.controllers;


import com.jpa.thepersistencelayerandjpa.ThePersistenceLayer.dto.EmployeeDTO;
import com.jpa.thepersistencelayerandjpa.ThePersistenceLayer.repositories.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

//    @GetMapping(path = "/getSecretMessage")
//    public String getMySuperSecretMessage() {
//        return "Secret Message: asdfal@#$DASD";
//    }

    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


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




