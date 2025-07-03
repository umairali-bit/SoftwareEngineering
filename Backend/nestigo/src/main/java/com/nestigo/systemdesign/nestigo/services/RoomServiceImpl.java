package com.nestigo.systemdesign.nestigo.services;

import com.nestigo.systemdesign.nestigo.dtos.RoomDTO;
import com.nestigo.systemdesign.nestigo.repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{

    private final RoomRepository roomRepository;


    @Override
    public RoomDTO createRoom(RoomDTO roomDTO) {
        return null;
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
