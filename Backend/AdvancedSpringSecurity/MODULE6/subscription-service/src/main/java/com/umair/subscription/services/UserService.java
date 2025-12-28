package com.umair.subscription.services;


import com.umair.subscription.dto.SignupRequest;
import com.umair.subscription.entities.SubscriptionEntity;
import com.umair.subscription.entities.UserEntity;
import com.umair.subscription.entities.enums.PlanType;
import com.umair.subscription.entities.enums.SubscriptionStatus;
import com.umair.subscription.repositories.SubscriptionRepository;
import com.umair.subscription.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public UserEntity registerWithFreePlan(SignupRequest request) {

        // validate the email
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use" + request.getEmail());
        }

        //Map DTO to Entity
        UserEntity user = modelMapper.map(request, UserEntity.class);

        //TODO: replace with passwordEncoder.encode(request.getPassword())

        UserEntity savedUser = userRepository.save(user);

        // create FREE subscription
        LocalDateTime  now = LocalDateTime.now();

        SubscriptionEntity subscription = new SubscriptionEntity();
        subscription.setUser(savedUser);
        subscription.setPlan(PlanType.FREE);
        subscription.setSubscriptionStatus(SubscriptionStatus.ACTIVE);
        subscription.setStartAt(now);
        subscription.setEndAt(null);

        subscriptionRepository.save(subscription);

        return savedUser;
    }



}
