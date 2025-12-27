package com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.services;


import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.dtos.LoginDTO;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.dtos.LoginResponseDTO;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final SessionService sessionService;


    public LoginResponseDTO login(LoginDTO inputLogin) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(inputLogin.getEmail(), inputLogin.getPassword())
        );

        UserEntity user = (UserEntity) authentication.getPrincipal();

        assert user != null;
        String accessToken = jwtService.generateAccessJwtToken(user);
        String refreshToken = jwtService.generateRefreshJwtToken(user);

        sessionService.generateNewSession(user,refreshToken);

        return new LoginResponseDTO(user.getId(), refreshToken, accessToken);

    }

    public LoginResponseDTO refreshToken(String refreshToken) {

        log.info("Incoming refreshToken startsWith: {}", refreshToken.substring(0, 15));


        sessionService.validateSession(refreshToken);

        Long userId = jwtService.getUserIdFromJwtToken(refreshToken);
        UserEntity user = userService.getUserById(userId);

        String accessToken = jwtService.generateAccessJwtToken(user);

        return new LoginResponseDTO(user.getId(), refreshToken, accessToken);
    }


}
