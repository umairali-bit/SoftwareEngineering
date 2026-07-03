package com.example.cachingApp.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalaryAccountEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal balance;

    @OneToOne
    @JoinColumn(name = "employee_id", unique = true)
    private EmployeeEntity employee;
}
