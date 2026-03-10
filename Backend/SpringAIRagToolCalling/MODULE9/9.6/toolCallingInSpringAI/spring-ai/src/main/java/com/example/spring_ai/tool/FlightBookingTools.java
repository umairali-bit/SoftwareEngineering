package com.example.spring_ai.tool;


import com.example.spring_ai.dto.BookingResponse;
import com.example.spring_ai.service.FlightBookingServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.time.Instant;

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
}
