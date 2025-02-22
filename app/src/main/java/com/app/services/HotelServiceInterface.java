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

    //validaciones
    void validateDTO(HotelDTO hotelDTO);
    void validateAvailability(Hotel hotel, Long id);
    void validateNonBooked(Hotel hotel,Long id);
    void validateDates(HotelDTO hotelDTO);
    void validateNonEmptyList(List<Hotel> list);
    void validateNonDuplicateHotel(Hotel hotel);

    //auxiliares
    List<Hotel> getTrueList();
    boolean compareObjects(Hotel object1, Hotel object2);

    //conversores
    HotelDTO mapToDTO(Hotel hotel);
    Hotel mapToEntity(HotelDTO hotelDTO);
}
