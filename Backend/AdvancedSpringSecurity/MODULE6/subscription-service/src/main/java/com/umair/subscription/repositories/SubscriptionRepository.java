package com.umair.subscription.repositories;


import com.umair.subscription.entities.SubscriptionEntity;
import org.springframework.boot.autoconfigure.pulsar.PulsarProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Integer> {

    @Query("""
         select s from SubscriptionEntity s\s
         where s.user.id = :userId
         and s.subscriptionStatus in (
            com.umair.subscription.entities.enums.SubscriptionStatus.ACTIVE,
            com.umair.subscription.entities.enums.SubscriptionStatus.CANCELED
            )
           \s
            and s.startAt <= now
            and (s.endAt is null or s.endAt > : now)
            order by s.startAt desc
           \s""")
    Optional <SubscriptionEntity> findCurrentEntitlement(Long userId, LocalDateTime now);
}
