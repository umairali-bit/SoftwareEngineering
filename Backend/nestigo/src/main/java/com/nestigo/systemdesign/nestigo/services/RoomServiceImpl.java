package com.nestigo.systemdesign.nestigo.services;

import com.nestigo.systemdesign.nestigo.dtos.RoomDTO;
import com.nestigo.systemdesign.nestigo.entities.HotelEntity;
import com.nestigo.systemdesign.nestigo.entities.RoomEntity;
import com.nestigo.systemdesign.nestigo.exceptions.ResourceNotFoundException;
import com.nestigo.systemdesign.nestigo.repositories.HotelRepository;
import com.nestigo.systemdesign.nestigo.repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;


    @Override
    public RoomDTO createRoom(Long hotelId, RoomDTO roomDTO) {
        log.info("Creating a room in hotel with ID: {}", hotelId);


    //  checking if the hotel exists or not
        HotelEntity hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel NOT found with ID: " + hotelId));

        RoomEntity room = modelMapper.map(roomDTO, RoomEntity.class);
        room.setHotel(hotel);
        room = roomRepository.save(room);



        return modelMapper.map(room, RoomDTO.class);

    //    TODO: add inventory
    }

    @Override
    public List<RoomDTO> getAllRoomsByHotel(Long hotelIDd) {
        return null;
    }

    @Override
    public RoomDTO getRoomById(Long id) {
        return null;
    }

    @Override
    public void deleteRoomById(Long roomId) {

    }
}
