package com.example.spring_ai.service;

import com.example.spring_ai.entity.BookingStatus;
import com.example.spring_ai.entity.FlightBooking;
import com.example.spring_ai.repository.FlightBookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final FlightBookingRepository flightBookingRepository;


    @Override
    public FlightBooking createFlightBooking(String userId, String destination, Instant departureTime) {
        return null;
    }

    @Override
    public List<FlightBooking> getUserBookings(String userId) {
        return List.of();
    }

    @Override
    public FlightBooking updateFlightBooking(Long bookingId, String userId, BookingStatus newStatus) {
        return null;
    }
}
