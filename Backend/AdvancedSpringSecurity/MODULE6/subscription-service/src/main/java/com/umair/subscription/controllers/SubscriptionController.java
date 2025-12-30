package com.umair.subscription.controllers;


import com.umair.subscription.dto.UpgradeRequestDTO;
import com.umair.subscription.dto.UpgradeResponseDTO;
import com.umair.subscription.entities.SubscriptionEntity;
import com.umair.subscription.entities.enums.PlanType;
import com.umair.subscription.repositories.SubscriptionRepository;
import com.umair.subscription.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final UserService userService;

    // what plan do I effectively has right now
    @GetMapping("/effective-plan")
    public ResponseEntity<Map<String, String>> getEffectivePlan(@RequestParam Long userId) {

        PlanType planType = userService.getEffectivePlan(userId);
        return ResponseEntity.ok(Map.of("plan", planType.name()));
    }


    @PostMapping("/upgrade")
    public ResponseEntity<?> upgrade(@RequestBody UpgradeRequestDTO request) {

        SubscriptionEntity saved = userService.upgradeNow(request.getUserId(), request.getPlan());

        return ResponseEntity.ok(new UpgradeResponseDTO(
                saved.getId(),
                saved.getPlan().name(),
                saved.getSubscriptionStatus().name(),
                saved.getStartAt(),
                saved.getEndAt()
        ));
    }

    @PreAuthorize("@subAuth.hasAtLeast(authentication, T(com.umair.subscription.entities.enums.PlanType).BASIC)")
    @GetMapping("/basic-feature")
    public ResponseEntity<String> basicFeature() {
        return ResponseEntity.ok("BASIC+ access");
    }

    @PreAuthorize("@subAuth.hasPlan(authentication, T(com.umair.subscription.entities.enums.PlanType).PREMIUM)")
    @GetMapping("/premium-feature")
    public ResponseEntity<String> getPremiumFeature() {
        return ResponseEntity.ok("PREMIUM only");
    }






}
