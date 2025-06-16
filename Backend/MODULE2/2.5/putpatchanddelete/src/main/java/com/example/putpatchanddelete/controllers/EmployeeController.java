package com.example.putpatchanddelete.controllers;

import com.example.putpatchanddelete.dto.EmployeeDTO;
import com.example.putpatchanddelete.services.EmployeeService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long employeeId) {
       Optional <EmployeeDTO> employeeDTO = employeeService.getEmployeeById(employeeId);
       return employeeDTO
               .map(employeeDTO1 -> ResponseEntity.ok(employeeDTO1))
               .orElse(ResponseEntity.notFound().build());
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
    public boolean deleteEmployeeById(@PathVariable Long employeeId) {
        return employeeService.deleteEmployeeById(employeeId);
    }

    @PatchMapping(path = "/{employeeId}")
    public EmployeeDTO updatePartialEmployeeById(@RequestBody Map<String, Object> updates,
                                             @PathVariable Long employeeId) {
        return employeeService.updatePartialEmployeeById(employeeId, updates);
    }


}



