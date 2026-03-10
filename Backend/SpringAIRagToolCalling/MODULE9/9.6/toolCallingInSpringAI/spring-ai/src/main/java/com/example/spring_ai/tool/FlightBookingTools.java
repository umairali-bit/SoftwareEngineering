package com.example.spring_ai.tool;


import com.example.spring_ai.dto.BookingListResponse;
import com.example.spring_ai.dto.BookingResponse;
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
            name = "get_user_booking",
            description = "Retreive all flight bookings for the current user, sorted by departure time (most recent first)."
    )
    public BookingListResponse getUserBooking(
            @ToolParam(description = "The unique user ID")
            String userId ) {
        List<FlightBooking> bookings = flightBookingService.getUserBookings(userId);

        List<BookingResponse> responses = bookings.stream()
                .map(b -> new BookingResponse(
                        b.getId(),
                        b.getDestination(),
                        b.getDepartureTime(),
                        b.getBookingStatus()

                ))
                .toList();

        String message = bookings.isEmpty() ? "You have no upcoming flight bookings." : "Here are your current flight bookings";

        return new BookingListResponse(responses, message);



    }



}
