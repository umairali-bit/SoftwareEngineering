package com.nestigo.systemdesign.nestigo.dtos;

import com.nestigo.systemdesign.nestigo.entities.GuestEntity;
import com.nestigo.systemdesign.nestigo.entities.HotelEntity;
import com.nestigo.systemdesign.nestigo.entities.RoomEntity;
import com.nestigo.systemdesign.nestigo.entities.UserEntity;
import com.nestigo.systemdesign.nestigo.entities.enums.BookingStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class BookingDTO {

    private Long id;

    private HotelEntity hotel;

    private RoomEntity room;


    private UserEntity user;


    private Integer roomsCount;


    private LocalDate checkInDate;


    private LocalDate checkOutDate;


    private LocalDateTime createdAt;


    private LocalDateTime updatedAT;



    private BookingStatus bookingStatus;


    private Set<GuestEntity> guests = new HashSet<>();


}
