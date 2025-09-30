package com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.dtos;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class EmployeeDTO {

    private Long id;

    private String name;

    private String email;


    private Integer age;


    private String role;

    private LocalDate dateOfJoining;

    private Boolean isActive;

}
