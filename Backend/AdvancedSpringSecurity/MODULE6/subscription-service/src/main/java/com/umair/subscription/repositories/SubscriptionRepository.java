package com.umair.subscription.repositories;


import com.umair.subscription.entities.SubscriptionEntity;
import org.springframework.boot.autoconfigure.pulsar.PulsarProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Integer> {
}
