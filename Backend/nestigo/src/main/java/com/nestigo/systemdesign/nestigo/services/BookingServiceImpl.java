package com.nestigo.systemdesign.nestigo.services;

import com.nestigo.systemdesign.nestigo.dtos.BookingDTO;
import com.nestigo.systemdesign.nestigo.dtos.BookingRequestDTO;
import com.nestigo.systemdesign.nestigo.dtos.HotelDTO;
import com.nestigo.systemdesign.nestigo.entities.*;
import com.nestigo.systemdesign.nestigo.entities.enums.BookingStatus;
import com.nestigo.systemdesign.nestigo.exceptions.ResourceNotFoundException;
import com.nestigo.systemdesign.nestigo.repositories.BookingRepository;
import com.nestigo.systemdesign.nestigo.repositories.HotelRepository;
import com.nestigo.systemdesign.nestigo.repositories.InventoryRepository;
import com.nestigo.systemdesign.nestigo.repositories.RoomRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public BookingDTO initializeBooking(BookingRequestDTO bookingRequestDTO) {

        log.info("initializeBooking booking for hotel: {}, room: {}. date {}-{}", bookingRequestDTO.getHotelId(),
                bookingRequestDTO.getRoomId(), bookingRequestDTO.getCheckInDate(), bookingRequestDTO.getCheckOutDate());

        HotelEntity hotel = hotelRepository.findById(bookingRequestDTO.getHotelId()).orElseThrow(() ->
                new ResourceNotFoundException("Hotel NOT found with id:"+ bookingRequestDTO.getHotelId()));

        RoomEntity room = roomRepository.findById(bookingRequestDTO.getRoomId()).orElseThrow(() ->
                new ResourceNotFoundException("Room NOT found with id:"+ bookingRequestDTO.getRoomId()));

        List<InventoryEntity> inventoryList = inventoryRepository.findAndLockInventory(room.getId(),
                bookingRequestDTO.getCheckInDate(), bookingRequestDTO.getCheckOutDate(),bookingRequestDTO.getRoomsCount());

        long daysCount = ChronoUnit.DAYS.between(bookingRequestDTO.getCheckInDate(), bookingRequestDTO.getCheckOutDate())+1;
        if (inventoryList.size() != daysCount) {
            throw new IllegalStateException("Room is not available");
        }

        // reserving the room
         for(InventoryEntity inventory : inventoryList) {
             inventory.setBookedCount(inventory.getBookedCount()+ bookingRequestDTO.getRoomsCount());
         }

         inventoryRepository.saveAll(inventoryList);

        UserEntity user = new UserEntity();
        user.setId(1L); //TODO: Remove Dummy User

        //TODO: calculate the dynamic price

         //create a booking
        BookingEntity booking =BookingEntity.builder()
                .bookingStatus(BookingStatus.RESERVED)
                .hotel(hotel)
                .room(room)
                .checkInDate(bookingRequestDTO.getCheckInDate())
                .checkOutDate(bookingRequestDTO.getCheckOutDate())
                .user(user)
                .roomsCount(bookingRequestDTO.getRoomsCount())
                .price(BigDecimal.TEN)
                .build();

        booking = bookingRepository.save(booking);
        return modelMapper.map(booking, BookingDTO.class);


    }
}
