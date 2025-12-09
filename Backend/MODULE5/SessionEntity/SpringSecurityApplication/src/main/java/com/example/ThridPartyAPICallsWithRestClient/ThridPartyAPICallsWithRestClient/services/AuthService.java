package com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.services;


import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.dtos.LoginDTO;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final SessionService sessionService;


    public String login(LoginDTO inputLogin) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(inputLogin.getEmail(), inputLogin.getPassword())
        );

        UserEntity user = (UserEntity) authentication.getPrincipal();

        String token = jwtService.generateJwtToken(user);

        Instant expiry = jwtService.getExpirationTimeFromJwtToken(token);

        sessionService.createOrReplaceSession(user, token, expiry);

        return token;



    }

    public void logout(String token) {
        sessionService.revokeByToken(token);
    }

}
