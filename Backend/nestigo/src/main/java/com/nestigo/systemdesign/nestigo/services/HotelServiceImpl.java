package com.nestigo.systemdesign.nestigo.services;

import com.nestigo.systemdesign.nestigo.dtos.HotelDTO;
import com.nestigo.systemdesign.nestigo.repositories.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService{


    private final HotelRepository hotelRepository;


    @Override
    public HotelDTO createHotel(HotelDTO hotelDTO) {
        log.info("Creating a new hotel with name: {}", hotelDTO.getName());

        return null;
    }

    @Override
    public HotelDTO getHotelById(Long id) {
        return null;
    }
}
