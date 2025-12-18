package com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.dtos;


import lombok.Data;

@Data
public class LoginResponseDTO {

    private Long id;
    private String accessToken;
    private String refreshToken;

}
