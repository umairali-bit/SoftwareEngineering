package com.nestigo.systemdesign.nestigo.services;

import com.nestigo.systemdesign.nestigo.dtos.HotelDTO;
import com.nestigo.systemdesign.nestigo.entities.HotelEntity;
import com.nestigo.systemdesign.nestigo.exceptions.ResourceNotFoundException;
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
        log.info("Created a new hotel with ID {}", hotelDTO.getId());


        //converting entity back to DTO
        return modelMapper.map(hotelEntity, HotelDTO.class);
    }

    @Override
    public HotelDTO getHotelById(Long id) {
        log.info("Getting the hotel with ID: {}", id);
        HotelEntity hotelEntity = hotelRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Hotel NOT found with ID:" + id));



        return modelMapper.map(hotelEntity,HotelDTO.class);
    }

    @Override
    public HotelDTO updateHotel(Long id, HotelDTO hotelDTO) {
        log.info("Getting the hotel with ID: {}", id);

        //getting hotel by id
        HotelEntity existingHotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID"));

        modelMapper.map(hotelDTO, existingHotel);

        HotelEntity updatedHotel = hotelRepository.save(existingHotel);
        return modelMapper.map(updatedHotel, HotelDTO.class);

    }

    @Override
    public void deleteHotelById(Long id) {
        boolean exists = hotelRepository.existsById(id);
        if(!exists) throw new ResourceNotFoundException("Hotel NOT found with ID:" + id);

        hotelRepository.deleteById(id);

        //TODO: delete the future inventories for this hotel


    }
}
