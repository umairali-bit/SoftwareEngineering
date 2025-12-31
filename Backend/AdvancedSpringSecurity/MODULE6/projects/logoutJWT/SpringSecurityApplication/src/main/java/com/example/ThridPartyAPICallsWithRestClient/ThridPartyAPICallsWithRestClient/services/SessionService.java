package com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.services;


import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.entities.SessionEntity;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.entities.UserEntity;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.exceptions.ResourceNotFoundException;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.repositories.SessionRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SessionService {

    private final SessionRepository sessionRepository;
    private final int SESSION_LIMIT = 2;

    @Transactional
    public void generateNewSession(UserEntity user, String refreshToken) {
        List<SessionEntity> userSessions = sessionRepository.findByUser(user);
        if (userSessions.size() >= SESSION_LIMIT) {
            userSessions.sort(Comparator.comparing(SessionEntity::getLastUsedAt));

            SessionEntity leastRecentlyUsedSession = userSessions.get(0);
            sessionRepository.delete(leastRecentlyUsedSession);
        }

        SessionEntity newSession = SessionEntity.builder()
                .user(user)
                .refreshToken(refreshToken)
                .build();
        sessionRepository.save(newSession);
    }
    @Transactional
    public void validateSession(String refreshToken) {
        SessionEntity session = sessionRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new SessionAuthenticationException("Session not found for refreshToken: "));
        session.setLastUsedAt(LocalDateTime.now());
        sessionRepository.save(session);
    }

    @Transactional
    public void logout(String refreshToken) {
        log.info("logout() called. token present? {}", refreshToken != null && !refreshToken.isBlank());
        if(refreshToken == null || refreshToken.isBlank()) return;


        var sessionOpt = sessionRepository.findByRefreshToken(refreshToken);
        log.info("Session exists in DB for that token? {}", sessionOpt.isPresent());

        sessionOpt.ifPresent(s -> log.info("Deleting session id={}, userId={}", s.getId(), s.getUser().getId()));

        sessionRepository.deleteByRefreshToken(refreshToken);

        boolean stillExists = sessionRepository.findByRefreshToken(refreshToken).isPresent();
        log.info("Session still exists after delete? {}", stillExists);


    }


}
