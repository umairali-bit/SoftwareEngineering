package com.nestigo.systemdesign.nestigo.repositories;

import com.nestigo.systemdesign.nestigo.entities.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {

}
