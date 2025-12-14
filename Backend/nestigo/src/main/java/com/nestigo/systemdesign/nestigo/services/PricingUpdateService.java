package com.nestigo.systemdesign.nestigo.services;


import com.nestigo.systemdesign.nestigo.entities.HotelEntity;
import com.nestigo.systemdesign.nestigo.repositories.HotelMinPriceRepository;
import com.nestigo.systemdesign.nestigo.repositories.HotelRepository;
import com.nestigo.systemdesign.nestigo.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PricingUpdateService {

    //schedular to update the inventory and hotelMinPrice every hour

    private final HotelRepository hotelRepository;
    private final InventoryRepository inventoryRepository;
    private final HotelMinPriceRepository hotelMinPriceRepository;

    public void updatePrices() {



    }


}
