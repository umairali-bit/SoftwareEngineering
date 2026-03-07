package com.example.spring_ai.dtos;


import lombok.*;

import javax.validation.constraints.NotNull;
import java.security.PrivateKey;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightBookingResponseDto {

    private Long id;

    private String userId;

    private String destination;

    private Instant departureTime;

    private Instant bookedAt;

}
