package com.example.Mappings.Mappings.controllers;


import com.example.Mappings.Mappings.entities.DepartmentEntity;
import com.example.Mappings.Mappings.services.DepartmentService;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
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

    @PutMapping(path = "/{departmentId}/manager/{employeeId}")
    public DepartmentEntity assignManager (@PathVariable Long departmentId,
                                           @PathVariable Long employeeId) {
        return departmentService.assignManager(departmentId,employeeId);

    }

    @GetMapping(path = "/assignedManager/{employeeId}")
    public DepartmentEntity getAssignedManager(@PathVariable Long employeeId) {
        return departmentService.getAssignedManager(employeeId);
    }

















}
