package com.umair.subscription.utils;


import com.umair.subscription.entities.UserEntity;
import com.umair.subscription.entities.enums.PlanType;
import com.umair.subscription.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("subAuth")
@RequiredArgsConstructor
public class SubscriptionAuthorization {

    private final UserService userService;

    public boolean hasAtLeast(Authentication auth, PlanType planType) {

        UserEntity user = (UserEntity) auth.getPrincipal();
        PlanType effective = userService.getEffectivePlan(user.getId());

        return effective.ordinal() >= planType.ordinal(); //FREE < BASIC < PREMIUM
    }

    public boolean hasPlan(Authentication auth, PlanType required) {
        UserEntity user = (UserEntity) auth.getPrincipal();
        return userService.getEffectivePlan(user.getId()) == required;
    }
}
