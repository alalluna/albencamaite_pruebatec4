package com.app.services;

import com.app.dtos.HotelDTO;
import com.app.entities.Hotel;

import java.util.List;

public interface HotelServiceInterface {
    List<HotelDTO> list();
    HotelDTO findOne(Long id);
    HotelDTO create( HotelDTO hotelDTO);
    HotelDTO update(Long id,  HotelDTO hotelDTO);
    void delete(Long id);

    //conversores
    HotelDTO mapToDTO(Hotel hotel);
}
