package com.umair.subscription.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonPropertyOrder({ "id", "name", "email" })
public class UserDTO {


    private Long id;
    private String name;
    private String email;
}
