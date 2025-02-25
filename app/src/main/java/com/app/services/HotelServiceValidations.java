package com.app.services;

import com.app.dtos.HotelDTO;
import com.app.entities.Hotel;
import org.springframework.http.HttpStatus;

import java.util.List;

public class HotelServiceValidations {
    //validaciones
    public static void validateDTO(HotelDTO hotelDTO) {
        if (hotelDTO.getHotelName() == null || hotelDTO.getHotelName().isBlank()) {
            throw new HotelServiceException("El nombre del hotel es obligatorio", HttpStatus.BAD_REQUEST.value());
        }
        if (hotelDTO.getCity() == null || hotelDTO.getCity().isBlank()) {
            throw new HotelServiceException("La ciudad es obligatoria", HttpStatus.BAD_REQUEST.value());
        }
        if (hotelDTO.getPrice() == null || hotelDTO.getPrice() < 0) {
            throw new HotelServiceException("El precio debe ser un número positivo", HttpStatus.BAD_REQUEST.value());
        }
    }

    public static void validateHotelDTOData(HotelDTO hotelDTO){
        validateDTO(hotelDTO);
        DateUtilService.validateDates(hotelDTO.getDateFrom(), hotelDTO.getDateTo());

    }

    public static void validateNonEmptyList(List<Hotel> list) {
        if (list.isEmpty()) {
            throw new HotelServiceException("No hay habitaciones disponibles", HttpStatus.NOT_FOUND.value());
        }
    }

    public static void validateAvailability(Hotel hotel, Long id) {
        if (!hotel.isAvailable()) {
            throw new HotelServiceException("Habitación " + id + " eliminada si desea recuperarla contacte con supervisión", HttpStatus.NOT_FOUND.value());
        }
        if (hotel.isBooked()) {
            throw new HotelServiceException("Habitación " + id +" reservada, si desea eliminar/modificar contacte con supervisión", HttpStatus.NOT_FOUND.value());
        }
    }
}
