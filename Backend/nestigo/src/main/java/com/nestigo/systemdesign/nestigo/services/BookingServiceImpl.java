package com.nestigo.systemdesign.nestigo.services;

import com.nestigo.systemdesign.nestigo.dtos.BookingDTO;
import com.nestigo.systemdesign.nestigo.dtos.BookingRequestDTO;
import com.nestigo.systemdesign.nestigo.dtos.HotelDTO;
import com.nestigo.systemdesign.nestigo.entities.HotelEntity;
import com.nestigo.systemdesign.nestigo.entities.RoomEntity;
import com.nestigo.systemdesign.nestigo.exceptions.ResourceNotFoundException;
import com.nestigo.systemdesign.nestigo.repositories.BookingRepository;
import com.nestigo.systemdesign.nestigo.repositories.HotelRepository;
import com.nestigo.systemdesign.nestigo.repositories.InventoryRepository;
import com.nestigo.systemdesign.nestigo.repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final InventoryRepository inventoryRepository;

    @Override
    @Transactional
    public BookingDTO initializeBooking(BookingRequestDTO bookingRequestDTO) {

        HotelEntity hotel = hotelRepository.findById(bookingRequestDTO.getHotelId()).orElseThrow(() ->
                new ResourceNotFoundException("Hotel NOT found with id:"+ bookingRequestDTO.getHotelId()));

        RoomEntity room = roomRepository.findById(bookingRequestDTO.getRoomId()).orElseThrow(() ->
                new ResourceNotFoundException("Room NOT found with id:"+ bookingRequestDTO.getRoomId()));


    }
}
