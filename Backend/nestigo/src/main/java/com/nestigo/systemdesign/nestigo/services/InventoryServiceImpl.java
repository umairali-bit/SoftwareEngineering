package com.nestigo.systemdesign.nestigo.services;

import com.nestigo.systemdesign.nestigo.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService{

    private final InventoryRepository inventoryRepository;


    @Override
    public void initializeRoomForAYear(Long roomId) {

    }
}
