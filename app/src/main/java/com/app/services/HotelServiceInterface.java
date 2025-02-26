package com.app.services;

import com.app.dtos.HotelBookingDTO;
import com.app.dtos.HotelDTO;
import com.app.dtos.HotelSummaryDTO;
import com.app.entities.Hotel;
import com.app.entities.HotelBooking;
import com.app.entities.User;
import java.util.List;

public interface HotelServiceInterface {
    List<HotelSummaryDTO> listHotelsSummary();
    List<HotelDTO> listByHotel(String hotelName);
    List<HotelDTO> list();
    HotelDTO findOne(Long id);
    HotelDTO create( HotelDTO hotelDTO);
    HotelDTO update(Long id,  HotelDTO hotelDTO);
    void delete(Long id);
    void deleteAllHotel (String hotelName);
    List<HotelDTO> filterHotels(String dateFrom, String dateTo, String destination);
    HotelBookingDTO createBooking(HotelBookingDTO hotelBookingDTO);

    //auxiliares
    void validateNonDuplicateHotel(Hotel hotel);
    List<Hotel> getTrueList();
    boolean compareObjects(Hotel objectOne, Hotel objectTwo);
    void updateHotelData(Hotel hotel, HotelDTO hotelDTO);
    Hotel findAvailableHotel(HotelBookingDTO hotelBookingDTO);
    HotelBooking createNewBooking(HotelBookingDTO hotelBookingDTO, Hotel hotel);
    List<User> createUserList(HotelBookingDTO hotelBookingDTO, HotelBooking booking);
    HotelBookingDTO buildBookingDTO(HotelBooking booking, Hotel hotel, List<User> hosts);

    //conversores
    HotelDTO mapToDTO(Hotel hotel);
    Hotel mapToEntity(HotelDTO hotelDTO);
}
