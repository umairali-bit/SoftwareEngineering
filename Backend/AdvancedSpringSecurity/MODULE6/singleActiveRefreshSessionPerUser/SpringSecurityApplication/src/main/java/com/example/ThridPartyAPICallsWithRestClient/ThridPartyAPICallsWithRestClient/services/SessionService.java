
package com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.services;

import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.entities.SessionEntity;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.entities.UserEntity;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.repositories.SessionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class SessionService {

    private final SessionRepository sessionRepository;
    private final JwtService jwtService;
    private final RefreshTokenHasher hasher;


    /**
     * Call this on LOGIN:
     * - kill all old sessions for this user
     * -create fresh session wit new refresh token hash
     */

    @Transactional
    public void generateNewSession(UserEntity user,  String refreshToken) {

        //remove all session by the user
        sessionRepository.deleteByUserId(user.getId());


        SessionEntity newSession = SessionEntity.builder()
                .user(user)
                .refreshTokenHash(hasher.sha256(refreshToken))
                .lastUsedAt(LocalDateTime.now())
                .build();

        sessionRepository.save(newSession);

    }
    /**
     * Logout using refresh token from cookie
     * - delete the session if token is present
     */

    @Transactional
    public void logout(String rawRefreshToken) {
        if (rawRefreshToken == null ||rawRefreshToken.isBlank()) return;

        String hash = hasher.sha256(rawRefreshToken);
        sessionRepository.findByRefreshTokenHash(hash).ifPresent(session -> {
            sessionRepository.delete(session);
        });
    }




}
