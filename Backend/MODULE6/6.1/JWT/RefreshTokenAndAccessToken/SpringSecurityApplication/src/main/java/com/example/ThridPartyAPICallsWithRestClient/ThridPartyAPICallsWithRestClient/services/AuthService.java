package com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.services;


import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.dtos.LoginDTO;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    public String login(LoginDTO inputLogin) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(inputLogin.getEmail(), inputLogin.getPassword())
        );

        UserEntity user = (UserEntity) authentication.getPrincipal();

        assert user != null;
        String accessToken = jwtService.generateAccessJwtToken(user);
        String refreshToken = jwtService.generateRefreshJwtToken(user);







    }
}
