package com.nestigo.systemdesign.nestigo.controllers;


import com.nestigo.systemdesign.nestigo.dtos.HotelDTO;
import com.nestigo.systemdesign.nestigo.dtos.HotelInfoDTO;
import com.nestigo.systemdesign.nestigo.dtos.HotelSearchRequest;
import com.nestigo.systemdesign.nestigo.services.HotelService;
import com.nestigo.systemdesign.nestigo.services.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
@Slf4j
public class HotelBrowseController {

    private final InventoryService inventoryService;
    private final HotelService hotelService;

    @GetMapping("/search")
    public ResponseEntity<Page<HotelDTO>> searchHotels(@RequestBody HotelSearchRequest hotelSearchRequest) {
        Page<HotelDTO> page = inventoryService.searchHotels(hotelSearchRequest);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<HotelInfoDTO> getHotelInfo(@PathVariable("hotelId") String hotelId) {
        return ResponseEntity.ok(hotelService.getHotelInfoById(hotelId));
    }





}
