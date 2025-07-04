package com.nestigo.systemdesign.nestigo.services;


import com.nestigo.systemdesign.nestigo.entities.RoomEntity;

public interface InventoryService {

    void initializeRoomForAYear(RoomEntity room);

    void deleteFutureInventories (RoomEntity room);



}
