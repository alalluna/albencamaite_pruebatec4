package com.app.services;

import com.app.dtos.HotelDTO;
import com.app.entities.Hotel;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
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

    public static void validateDates(LocalDate dateFrom, LocalDate dateTo) {
        if (dateFrom.isAfter(dateTo)) {
            throw new HotelServiceException("La fecha de inicio no puede ser posterior a la fecha de fin", HttpStatus.BAD_REQUEST.value());
        }
        if (dateFrom.isBefore(LocalDate.now()) || dateTo.isBefore(LocalDate.now())) {
            throw new HotelServiceException("Las fechas deben ser futuras", HttpStatus.BAD_REQUEST.value());
        }
    }

    public static void validateObjectDates(HotelDTO hotelDTO){
        validateDates(hotelDTO.getDateFrom(), hotelDTO.getDateTo());
    }

    public static void validateNonEmptyList(List<Hotel> list) {
        if (list.isEmpty()) {
            throw new HotelServiceException("No hay habitaciones disponibles", HttpStatus.NOT_FOUND.value());
        }
    }

    public static void validateNonBooked(Hotel hotel, Long id){
        if (hotel.isBooked()) {
            throw new HotelServiceException("Habitación " + id +" reservada, si desea eliminarla debera cancelar/modificar la reserva", HttpStatus.NOT_FOUND.value());
        }
    }

    public static void validateAvailability(Hotel hotel, Long id) {
        if (!hotel.isAvailable()) {
            throw new HotelServiceException("Habitación " + id + " eliminada si desea recuperarla contacte con supervisión", HttpStatus.NOT_FOUND.value());
        }
    }
}
