package com.umair.subscription.services;


import com.umair.subscription.dto.SignupRequestDTO;
import com.umair.subscription.dto.UserDTO;
import com.umair.subscription.entities.SubscriptionEntity;
import com.umair.subscription.entities.UserEntity;
import com.umair.subscription.entities.enums.PlanType;
import com.umair.subscription.entities.enums.SubscriptionStatus;
import com.umair.subscription.repositories.SubscriptionRepository;
import com.umair.subscription.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDTO registerWithFreePlan(SignupRequestDTO request) {

        // validate the email
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use " + request.getEmail());
        }

        //Map DTO to Entity
        UserEntity user = modelMapper.map(request, UserEntity.class);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        UserEntity savedUser = userRepository.save(user);

        // create FREE subscription
        LocalDateTime  now = LocalDateTime.now();

        SubscriptionEntity subscription = new SubscriptionEntity();
        subscription.setUser(savedUser);
        subscription.setPlan(PlanType.FREE);
        subscription.setSubscriptionStatus(SubscriptionStatus.ACTIVE);
        subscription.setStartAt(now);
        subscription.setEndAt(null);

       SubscriptionEntity savingSubscription = subscriptionRepository.save(subscription);

        return modelMapper.map(savedUser, UserDTO.class);
    }

    public PlanType getEffectivePlan (Long userId) {
        LocalDateTime  now = LocalDateTime.now();

        return subscriptionRepository.findCurrentEntitlement(userId, now)
                .map(SubscriptionEntity -> SubscriptionEntity.getPlan())
                .orElse(PlanType.FREE);
    }

    @Transactional
    @Scheduled(cron = "0 0 * * * *")
    public void expireSubscriptions() {
        LocalDateTime now = LocalDateTime.now();

        int updated = subscriptionRepository.expireEndedSubscriptions(now);

        log.info("Expired {} subscriptions", updated);
        System.out.println("Expired subscriptions updated: " + updated);

    }



}
