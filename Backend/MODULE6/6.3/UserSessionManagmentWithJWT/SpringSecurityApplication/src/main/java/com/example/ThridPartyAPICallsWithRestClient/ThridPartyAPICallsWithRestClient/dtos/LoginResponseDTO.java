package com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginResponseDTO {

        private Long userId;
        private String refreshToken;
        private String accessToken;
    }


