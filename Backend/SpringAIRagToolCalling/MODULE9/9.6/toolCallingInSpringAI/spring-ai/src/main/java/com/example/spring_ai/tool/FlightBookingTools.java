package com.example.spring_ai.tool;


import com.example.spring_ai.dto.BookingListResponse;
import com.example.spring_ai.dto.BookingResponse;
import com.example.spring_ai.entity.BookingStatus;
import com.example.spring_ai.entity.FlightBooking;
import com.example.spring_ai.service.FlightBookingServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import javax.crypto.spec.DESedeKeySpec;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FlightBookingTools {
    private final FlightBookingServiceImpl flightBookingService;

    @Tool(
            name = "flight_booking_tool",
            description = "Create a new flight booking for a user"

    )
    public BookingResponse createFlightBooking(
            @ToolParam(description = "Unique user id")
            String userId,
            @ToolParam(description = "destination")
            String destination,
            @ToolParam(description = "departure time in ISO-8601 format")
            Instant departureTime) {

        var flightBooking = flightBookingService.createFlightBooking(userId, destination, departureTime);

        return new BookingResponse(
                flightBooking.getId(),
                flightBooking.getDestination(),
                flightBooking.getDepartureTime(),
                flightBooking.getBookingStatus()
        );


    }

    @Tool(
            name = "get_user_bookings",
            description = "Retrieve all flight bookings for the current user, sorted by departure time (most recent first). " +
                    "Returns an empty list message if none exist."
    )
    public BookingListResponse getUserBookings(
            @ToolParam(description = "The unique user ID")
            String userId
    ) {
        List<FlightBooking> bookings = flightBookingService.getUserBookings(userId);

        List<BookingResponse> responses = bookings.stream()
                .map(b -> new BookingResponse(
                        b.getId(),
                        b.getDestination(),
                        b.getDepartureTime(),
                        b.getBookingStatus()
                ))
                .toList();

        String message = bookings.isEmpty()
                ? "You have no upcoming flight bookings."
                : "Here are your current flight bookings:";

        return new BookingListResponse(responses, message);
    }


    @Tool(
            name = "update_booking_status",
            description = "Update the status of an existing flight booking (e.g., cancel it)." +
                    "Only the owner of the booking can modify it." +
                    "Common use: set status to CANCELLED."
    )
    public BookingResponse updateBookingStatus(
            @ToolParam(description = "The booking ID returned from create or get bookings", required = true)
            Long bookingId,

            @ToolParam(description = "The user ID who owns the booking", required = true)
            String userId,

            @ToolParam(description = "New status: CONFIRMED, CANCELLED, or PENDING", required = true)
            BookingStatus newStatus

    ) {
        FlightBooking updated = flightBookingService.updateFlightBooking(bookingId, userId, newStatus);
        return new BookingResponse(
                updated.getId(),
                updated.getDestination(),
                updated.getDepartureTime(),
                updated.getBookingStatus()
        );
    }


}
