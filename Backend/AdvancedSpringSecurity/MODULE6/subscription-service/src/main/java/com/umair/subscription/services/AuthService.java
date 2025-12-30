package com.umair.subscription.services;


import com.umair.subscription.dto.LoginRequestDTO;
import com.umair.subscription.dto.LoginResponseDTO;
import com.umair.subscription.entities.UserEntity;
import com.umair.subscription.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public LoginResponseDTO login (LoginRequestDTO request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserEntity user = (UserEntity) authentication.getPrincipal();


        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);


        return new LoginResponseDTO(user.getId(), accessToken, refreshToken);


    }


}
