package com.nestigo.systemdesign.nestigo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoomRepository extends JpaRepository<RoomRepository, Long> {
}
