package com.example.putpatchanddelete.controllers;

import com.example.putpatchanddelete.dto.EmployeeDTO;
import com.example.putpatchanddelete.services.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{employeeId}")
    public EmployeeDTO getEmployeeById(@PathVariable Long employeeId) {
        return employeeService.getEmployeeById(employeeId);
    }

    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PostMapping("/createPostman")
    public EmployeeDTO createEmployee(@RequestBody EmployeeDTO employee) {
        return employeeService.createEmployee(employee);
    }

    @PutMapping
    public String testingPut() {
        return "Hello from Put";
    }

    @PutMapping (path = "/{employeeId}")
    EmployeeDTO updateEmployeeById(@RequestBody EmployeeDTO employeeDTO, @PathVariable Long employeeId) {

        return employeeService.updateEmployeeById(employeeId, employeeDTO);
    }

    @DeleteMapping (path = "/{employeeId}")
    public void deleteEmployeeById(@PathVariable Long employeeId) {
        employeeService.deleteEmployeeById(employeeId);
    }

}



