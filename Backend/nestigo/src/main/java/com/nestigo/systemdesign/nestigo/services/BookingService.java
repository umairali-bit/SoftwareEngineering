package com.nestigo.systemdesign.nestigo.services;

import com.nestigo.systemdesign.nestigo.dtos.BookingDTO;
import com.nestigo.systemdesign.nestigo.dtos.BookingRequestDTO;

public interface BookingService {
    BookingDTO initializeBooking(BookingRequestDTO bookingRequestDTO);
}
