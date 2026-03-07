package com.example.spring_ai.dtos;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightBookingRequestDto {

    @NotNull
    private Long userId;

    @NotBlank
    private String destination;

    @NotNull
    private Instant departureTime;
}
