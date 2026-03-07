package com.example.spring_ai.service;


import com.example.spring_ai.entity.BookingStatus;
import com.example.spring_ai.entity.FlightBooking;

import java.time.Instant;
import java.util.List;

public interface BookingService {

    FlightBooking createFlightBooking(String userId, String destination, Instant departureTime);
    List<FlightBooking> getUserBookings(String userId);
    FlightBooking updateFlightBooking(Long bookingId, String userId, BookingStatus newStatus);

}
