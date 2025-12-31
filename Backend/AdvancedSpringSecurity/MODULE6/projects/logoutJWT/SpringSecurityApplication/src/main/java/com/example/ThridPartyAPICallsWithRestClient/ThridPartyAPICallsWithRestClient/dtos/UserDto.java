package com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.dtos;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Data
@JsonPropertyOrder({ "id", "name", "email" })
public class UserDto {

    private Long id;
    private String name;
    private String email;
}


