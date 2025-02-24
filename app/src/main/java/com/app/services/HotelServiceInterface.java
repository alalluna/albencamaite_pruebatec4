package com.app.services;

import com.app.dtos.HotelBookingDTO;
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
    List<HotelDTO> filterHotels(String dateFrom, String dateTo, String destination);
    HotelBookingDTO createBooking(HotelBookingDTO hotelBookingDTO);

    //auxiliares
    void validateNonDuplicateHotel(Hotel hotel);
    List<Hotel> getTrueList();
    boolean compareObjects(Hotel objectOne, Hotel objectTwo);
    void updateHotelData(Hotel hotel, HotelDTO hotelDTO);

    //conversores
    HotelDTO mapToDTO(Hotel hotel);
    Hotel mapToEntity(HotelDTO hotelDTO);
}
