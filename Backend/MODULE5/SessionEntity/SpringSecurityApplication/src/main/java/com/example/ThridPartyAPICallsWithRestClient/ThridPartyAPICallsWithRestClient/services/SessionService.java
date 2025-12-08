package com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.services;

import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.entities.SessionEntity;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.entities.UserEntity;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.repositories.SessionRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;

    @Transactional
    public void createOrReplaceSession(UserEntity user, String token, Instant jwtExpiry) {

        //removing any existing sessions from this user
        sessionRepository.deleteByUser(user);

        //create new session
        SessionEntity session = new SessionEntity();
        session.setUser(user);
        session.setToken(token);
        session.setCreatedAt(Instant.now());
        session.setExpiresAt(jwtExpiry);
        session.setRevoked(false);

        sessionRepository.save(session);


    }



}
