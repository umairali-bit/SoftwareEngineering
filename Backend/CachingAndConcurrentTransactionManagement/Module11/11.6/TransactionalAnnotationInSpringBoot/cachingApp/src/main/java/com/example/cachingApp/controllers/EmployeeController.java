package com.example.cachingApp.controllers;


import com.example.cachingApp.dtos.EmployeeDTO;
import com.example.cachingApp.dtos.SalaryAccountDTO;
import com.example.cachingApp.entities.SalaryAccountEntity;
import com.example.cachingApp.services.SalaryAccountService;
import com.example.cachingApp.services.impl.EmployeeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeServiceImpl employeeService;
    private final SalaryAccountService salaryAccountService;


    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable("employeeId") Long employeeId) {

        EmployeeDTO employeeDTO = employeeService.getEmployeeById(employeeId);

        return ResponseEntity.ok(employeeDTO);

    }

    @GetMapping()
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PostMapping()
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(employeeService.createEmployee(employeeDTO));
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("employeeId") Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/incrementBalance/{accountId}")
    public ResponseEntity<SalaryAccountDTO> incrementBalance(@PathVariable("accountId") Long accountId) {

       SalaryAccountDTO salaryAccount =  salaryAccountService.implementBalance(accountId);
       return ResponseEntity.ok(salaryAccount);
    }


}
