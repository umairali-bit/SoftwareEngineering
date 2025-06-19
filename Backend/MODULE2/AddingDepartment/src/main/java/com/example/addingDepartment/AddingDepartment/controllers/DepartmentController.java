package com.example.addingDepartment.AddingDepartment.controllers;


import com.example.addingDepartment.AddingDepartment.advices.ApiResponse;
import com.example.addingDepartment.AddingDepartment.dto.DepartmentDTO;
import com.example.addingDepartment.AddingDepartment.exceptions.ResourceNotFound;
import com.example.addingDepartment.AddingDepartment.services.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/departments")
@Validated
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long departmentId){
        Optional<DepartmentDTO> departmentDTO = departmentService.getDepartmentById(departmentId);
        return departmentDTO
                .map(departmentDTO1 -> ResponseEntity.ok(departmentDTO1))
                .orElseThrow(() -> new ResourceNotFound("Department NOT found with id: " + departmentId));
    }

    @PostMapping
    public ResponseEntity<List<DepartmentDTO>> createDepartments(@RequestBody List<@Valid DepartmentDTO> departmentDTO) {
        List<DepartmentDTO> savedDepartment = departmentService.createDepartments(departmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDepartment);
    }

    @PutMapping
    public ResponseEntity<List<DepartmentDTO>> updateDepartments(@RequestBody List<DepartmentDTO> dtos) {
        List<DepartmentDTO> updated = departmentService.updateDepartments(dtos);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping
    public ResponseEntity<ApiResponse<String>> deleteAllDepartments() {
        departmentService.deleteAllDepartments();
        ApiResponse<String> response = new ApiResponse<>("All departments deleted");
        return ResponseEntity.ok(response);
    }





}
