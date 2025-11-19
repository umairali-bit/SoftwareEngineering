package com.nestigo.systemdesign.nestigo.controllers;


import com.nestigo.systemdesign.nestigo.dtos.BookingDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/bookings")
public class HotelBookingController {

    public ResponseEntity<BookingDTO> initializeBookings(BookingDTO bookingDTO) {
        return ResponseEntity.ok(bookingDTO);
    }



}
