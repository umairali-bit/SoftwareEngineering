package com.example.spring_ai.service;


import com.example.spring_ai.entities.BookingStatus;
import com.example.spring_ai.entities.FlightBooking;
import com.example.spring_ai.repositories.FlightBookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightBookingService {

    private final FlightBookingRepository flightBookingRepository;

    //    create booking
    public FlightBooking createFlightBooking(String userId, String destination, Instant departureTime) {

//        if booking exists
        boolean exists = flightBookingRepository.existsByUserIdAndDestinationAndDepartureTime(
                userId, destination, departureTime);

        if (exists) {
            throw new IllegalArgumentException("flight booking already exists");
        }

//        creating a booking
        FlightBooking flightBooking = FlightBooking.builder()
                .userId(userId)
                .destination(destination)
                .departureTime(departureTime)
                .bookingStatus(BookingStatus.CONFIRMED)
                .build();

        return flightBookingRepository.save(flightBooking);

    }

//    get user's booking

    public List<FlightBooking> getUserBookings(String userId) {
        return flightBookingRepository.findUserByUserIdOrderByDepartureTimeDesc(userId);

    }

//    update booking

    public FlightBooking updateFlightBooking(Long bookingId, String userId, BookingStatus newStatus) {
        FlightBooking booking = flightBookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("booking id not found"));

        if (!booking.getUserId().equals(userId)) {
            throw new IllegalArgumentException("you can only modify your bookings");
        }

            booking.setBookingStatus(newStatus);
            return flightBookingRepository.save(booking);


        }


    }

