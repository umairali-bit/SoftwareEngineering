package com.nestigo.systemdesign.nestigo.repositories;

import com.nestigo.systemdesign.nestigo.entities.HotelEntity;
import com.nestigo.systemdesign.nestigo.entities.InventoryEntity;
import com.nestigo.systemdesign.nestigo.entities.RoomEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {

    void deleteByRoom(RoomEntity room);

    @Query("""
    SELECT DISTINCT i.hotel
    FROM InventoryEntity i
    WHERE i.city = :city
      AND i.date BETWEEN :startDate AND :endDate
      AND i.closed = false
      AND (i.totalCount - i.bookedCount) >= :roomsCount
    GROUP BY i.hotel, i.room
    HAVING COUNT(i.date) = :dateCount
""")
    Page<HotelEntity> findHotelsWithAvailableInventory(
            @Param("city") String city,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("roomsCount") Integer roomCount,
            @Param("dateCount") Long dateCount,
            Pageable pageable
    );


}
