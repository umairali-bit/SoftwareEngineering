package com.example.Mappings.Mappings.controllers;


import com.example.Mappings.Mappings.entities.DepartmentEntity;
import com.example.Mappings.Mappings.services.DepartmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }


    @GetMapping(path = "/{departmentId}")
    public DepartmentEntity getDepartmentById(@PathVariable Long departmentId) {
        return departmentService.getDepartmentById(departmentId);
    }

    @PostMapping
    public DepartmentEntity createNewDepartment (@RequestBody DepartmentEntity departmentEntity) {
        return departmentService.createNewDepartment(departmentEntity);





}

















}
