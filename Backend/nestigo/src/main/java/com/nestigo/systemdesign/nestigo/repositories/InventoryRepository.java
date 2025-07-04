package com.nestigo.systemdesign.nestigo.repositories;

import com.nestigo.systemdesign.nestigo.entities.InventoryEntity;
import com.nestigo.systemdesign.nestigo.entities.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {

    void deleteRoomByDateAfter(LocalDate date, RoomEntity room);

}
