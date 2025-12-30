package com.umair.subscription.services;


import com.umair.subscription.dto.LoginRequestDTO;
import com.umair.subscription.dto.LoginResponseDTO;
import com.umair.subscription.entities.SessionEntity;
import com.umair.subscription.entities.UserEntity;
import com.umair.subscription.entities.enums.SessionStatus;
import com.umair.subscription.repositories.SessionRepository;
import com.umair.subscription.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final SessionRepository sessionRepository;

    public LoginResponseDTO login (LoginRequestDTO request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserEntity user = (UserEntity) authentication.getPrincipal();


        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        SessionEntity session = new SessionEntity();
        session.setUser(user);
        session.setRefreshTokenHash(RefreshTokenHasher.sha256(refreshToken));
        session.setStatus(SessionStatus.ACTIVE);
        session.setExpiresAt(LocalDateTime.now().plusDays(30));

        sessionRepository.save(session);



        return new LoginResponseDTO(user.getId(), accessToken, refreshToken);


    }


}
