package com.jpa.thepersistencelayerandjpa.ThePersistenceLayer.controllers;


import com.jpa.thepersistencelayerandjpa.ThePersistenceLayer.dto.EmployeeDTO;
import com.jpa.thepersistencelayerandjpa.ThePersistenceLayer.entities.EmployeeEntity;
import com.jpa.thepersistencelayerandjpa.ThePersistenceLayer.repositories.EmployeeRepository;
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

    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @GetMapping(path = "/{employeeId}")
    public EmployeeEntity getEmployeeById(@PathVariable(name = "employeeId") Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @GetMapping
        public List<EmployeeEntity> getAllEmployees(@RequestParam Integer age) {

            return employeeRepository.findAll();
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
    public EmployeeEntity createNewEmployeeP(@RequestBody EmployeeEntity inputEmployee) {



        return employeeRepository.save(inputEmployee);

    }

    @PutMapping String testingPut() {
        return "Hello from Put";
    }
}




