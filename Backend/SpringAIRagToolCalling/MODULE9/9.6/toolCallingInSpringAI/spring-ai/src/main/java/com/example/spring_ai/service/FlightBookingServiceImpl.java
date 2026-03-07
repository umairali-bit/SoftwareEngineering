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
public class FlightBookingServiceImpl implements BookingService {

    private final FlightBookingRepository flightBookingRepository;


    @Override
    public FlightBooking createFlightBooking(String userId, String destination, Instant departureTime) {
//        if booking exists
        boolean exists = flightBookingRepository.existsByUserIdAndDestinationAndDepartureTime(
                userId,destination,departureTime
        );

        if (exists) {
            throw new IllegalArgumentException("booking already exists for " + destination);
        }

        FlightBooking booking = FlightBooking.builder()
                .userId(userId)
                .destination(destination)
                .departureTime(departureTime)
                .bookingStatus(BookingStatus.CONFIRMED)
                .build();



        return flightBookingRepository.save(booking);
    }

    @Override
    public List<FlightBooking> getUserBookings(String userId) {
        return flightBookingRepository.findByUserIdOrderByDepartureTimeDesc(userId);
    }

    @Override
    public FlightBooking updateFlightBooking(Long bookingId, String userId, BookingStatus newStatus) {
        return null;
    }
}
