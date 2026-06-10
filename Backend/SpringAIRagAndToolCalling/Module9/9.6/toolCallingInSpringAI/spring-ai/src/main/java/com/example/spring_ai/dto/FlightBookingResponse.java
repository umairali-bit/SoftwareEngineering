package com.example.spring_ai.dto;

import com.example.spring_ai.entities.BookingStatus;

import java.time.Instant;

public record FlightBookingResponse (Long id, String destination, Instant departureTime,
                                     BookingStatus bookingStatus) {}
