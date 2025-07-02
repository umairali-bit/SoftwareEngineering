package com.nestigo.systemdesign.nestigo.services;

import com.nestigo.systemdesign.nestigo.dtos.HotelDTO;
import com.nestigo.systemdesign.nestigo.entities.HotelEntity;
import com.nestigo.systemdesign.nestigo.repositories.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService{


    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;


    @Override
    public HotelDTO createHotel(HotelDTO hotelDTO) {
        log.info("Creating a new hotel with name: {}", hotelDTO.getName());

        //converting dto to entity
        HotelEntity hotelEntity = modelMapper.map(hotelDTO,HotelEntity.class);
        hotelEntity.setActive(false);
        hotelEntity = hotelRepository.save(hotelEntity);


        //converting entity back to DTO
        return modelMapper.map(hotelEntity, HotelDTO.class);
    }

    @Override
    public HotelDTO getHotelById(Long id) {
        return null;
    }
}
