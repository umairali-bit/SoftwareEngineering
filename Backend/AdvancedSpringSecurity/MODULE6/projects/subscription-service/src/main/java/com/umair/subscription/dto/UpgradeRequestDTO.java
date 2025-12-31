package com.umair.subscription.dto;

import com.umair.subscription.entities.enums.PlanType;
import lombok.Data;

@Data
public class UpgradeRequestDTO {

    private Long userId;
    private PlanType plan;
}
