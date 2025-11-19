package com.nestigo.systemdesign.nestigo.services;

import com.nestigo.systemdesign.nestigo.dtos.BookingDTO;
import com.nestigo.systemdesign.nestigo.dtos.BookingRequestDTO;
import com.nestigo.systemdesign.nestigo.repositories.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    @Override
    public BookingDTO initializeBooking(BookingRequestDTO bookingRequestDTO) {
        return null;
    }
}
