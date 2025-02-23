package com.app.services;

import com.app.dtos.HotelDTO;
import com.app.entities.Hotel;

import java.time.LocalDate;
import java.util.List;

public interface HotelServiceInterface {
    List<HotelDTO> list();
    HotelDTO findOne(Long id);
    HotelDTO create( HotelDTO hotelDTO);
    HotelDTO update(Long id,  HotelDTO hotelDTO);
    void delete(Long id);
    List<HotelDTO> filterHotels(LocalDate dateFrom, LocalDate dateTo, String destination);

    //validaciones
    //void validateDTO(HotelDTO hotelDTO);
    //void validateAvailability(Hotel hotel, Long id);
    //void validateNonBooked(Hotel hotel,Long id);
    //void validateObjectDates(HotelDTO hotelDTO);
    //void validateDates(LocalDate dateFrom, LocalDate dateTo);
    //void validateNonEmptyList(List<Hotel> list);
    void validateNonDuplicateHotel(Hotel hotel);

    //auxiliares
    List<Hotel> getTrueList();
    boolean compareObjects(Hotel object1, Hotel object2);
    void updateHotelData(Hotel hotel, HotelDTO hotelDTO);

    //conversores
    HotelDTO mapToDTO(Hotel hotel);
    Hotel mapToEntity(HotelDTO hotelDTO);
}
