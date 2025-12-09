package com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.services;

import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.entities.SessionEntity;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.entities.UserEntity;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.repositories.SessionRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;

    @Transactional
    public void createOrReplaceSession(UserEntity user, String token, Instant jwtExpiry) {

        //removing any existing sessions from this user
        sessionRepository.deleteByUserId(user.getId());

        //create new session
        SessionEntity session = sessionRepository.findByUserId(user.getId())
                .orElse(new SessionEntity());
        session.setUser(user);
        session.setToken(token);
        session.setCreatedAt(Instant.now());
        session.setExpiresAt(jwtExpiry);
        session.setRevoked(false);

        sessionRepository.save(session);


    }

    @Transactional
    public void revokeByToken(String token){
        sessionRepository.findByToken(token).ifPresent(session -> {
            session.setRevoked(true);
            sessionRepository.save(session);
        });
    }

    public boolean isTokenActive(String token) {
        return sessionRepository.findByToken(token)
                .map(s -> {
                    System.out.println("Session in DB: revoked=" + s.isRevoked()
                            + ", expiresAt=" + s.getExpiresAt()
                            + ", now=" + Instant.now());
                    return !s.isRevoked()
                            && (s.getExpiresAt() == null || s.getExpiresAt().isAfter(Instant.now()));
                })
                .orElse(false);
    }



}
