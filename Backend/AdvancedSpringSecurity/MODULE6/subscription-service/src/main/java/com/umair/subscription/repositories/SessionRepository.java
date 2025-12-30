package com.umair.subscription.repositories;

import com.umair.subscription.entities.SessionEntity;
import com.umair.subscription.entities.enums.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity,Long> {

    Optional<SessionEntity> findByRefreshTokenHashAndStatus(String refreshTokenHash, SessionStatus sessionStatus);

    @Modifying
    @Query("""
        update SessionEntity s
        set s.status = com.umair.subscription.entities.enums.SessionStatus.REVOKED
        where s.user.id = :userId
            and s.status = com.umair.subscription.entities.enums.SessionStatus.ACTIVE
""")
    int revokeAllActiveSessions(Long userId);


}
