package com.nestigo.systemdesign.nestigo.services;

import com.nestigo.systemdesign.nestigo.dtos.RoomDTO;

import java.util.List;

public interface RoomService {

    RoomDTO createRoom (RoomDTO roomDTO);

    List<RoomDTO> getAllRoomsByHotel (Long hotelIDd);

    RoomDTO getRoomById(Long id);

    void deleteRoomById(Long roomId);




}
