package com.example.spring_ai.tools;

import com.example.spring_ai.dto.BookingListResponse;
import com.example.spring_ai.dto.FlightBookingResponse;
import com.example.spring_ai.entities.BookingStatus;
import com.example.spring_ai.entities.FlightBooking;
import com.example.spring_ai.service.FlightBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FlightBookingTools {

    private final FlightBookingService flightBookingService;

    @Tool(
            name = "create_booking",
            description = "Create a new flight booking for a user")
    public FlightBookingResponse createBooking(
            @ToolParam(description = "A unique user ID, for example user123")
            String userId,
            @ToolParam(description = "Destination, for example Kuala Lumpur")
            String destination,
            @ToolParam(description = "Departure date and time in ISO format, for example 2026-06-10T14:30:00Z")
            Instant departureTime) {

        var flightBooking = flightBookingService.createFlightBooking(userId, destination, departureTime);


        return new FlightBookingResponse(
                flightBooking.getId(),
                flightBooking.getDestination(),
                flightBooking.getDepartureTime(),
                flightBooking.getBookingStatus()
        );
    }

    @Tool(
            name = "get_user_booking",
            description = "Retrieve all flight bookings for the user, sorted by departure time (most recent first). " +
                          "returns an empty list if none exits")
    public BookingListResponse getUserBookings(
            @ToolParam(description = "A unique user Id, like user123")
            String userId) {

        List<FlightBooking> bookings = flightBookingService.getUserBookings(userId);

        List<FlightBookingResponse> responses = bookings.stream().map(
                b -> new FlightBookingResponse(
                        b.getId(),
                        b.getDestination(),
                        b.getDepartureTime(),
                        b.getBookingStatus()

                )).toList();

        String message = bookings.isEmpty()
                ? "You have no upcoming flights"
                : "Here are the upcoming flights: ";

        return new BookingListResponse(responses, message);
    }

    @Tool(
            name = "update_booking",
            description = "Update the status of an existing booking. " +
                          "Only the owner of the booking can modify it. " +
                          "Common use: cancel a booking.")

    public FlightBookingResponse updateBooking(
            @ToolParam (description = "The booking id returned from create or get bookings", required = true)
            Long bookingId,
            @ToolParam (description = "The userId who owns the booking ", required = true)
            String userId,
            @ToolParam (description = "New status: Confirmed, Pending, Cancelled", required = true)
            BookingStatus newStatus) {

        FlightBooking updated = flightBookingService.updateFlightBooking(bookingId, userId, newStatus);

        return new FlightBookingResponse(
                updated.getId(),
                updated.getUserId(),
                updated.getDepartureTime(),
                updated.getBookingStatus()
        );

    }






}
