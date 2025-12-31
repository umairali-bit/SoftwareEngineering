package com.umair.subscription.dto;

import java.time.LocalDateTime;

public record UpgradeResponseDTO(

        Long subscriptionId,
        String plan,
        String status,
        LocalDateTime startAt,
        LocalDateTime endAt) {


}
