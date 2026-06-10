package com.example.spring_ai.dto;

import java.util.List;

public record BookingListResponse(List<FlightBookingResponse> bookings, String message) {}
