package com.app.services;

import com.app.dtos.FlightDTO;
import com.app.entities.Flight;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;

public class FlightServiceValidations {
    public static void validateDTO(FlightDTO flightDTO) {
        if(flightDTO.getFlightName() == null || flightDTO.getFlightName().isBlank()){
            throw new FlightServiceException( "El nombre de la compañía es obligatorio", HttpStatus.BAD_REQUEST.value());
        }
        if ( flightDTO.getCityFrom() == null || flightDTO.getCityFrom().isBlank()){
            throw new FlightServiceException( "La ciudad de origen es obligatoria", HttpStatus.BAD_REQUEST.value());
        }
        if ( flightDTO.getCityDestination() == null || flightDTO.getCityDestination().isBlank()){
            throw new FlightServiceException( "La ciudad de destino es obligatoria", HttpStatus.BAD_REQUEST.value());
        }
        if ( flightDTO.getTypeOfSeat() == null || flightDTO.getTypeOfSeat().isBlank()){
            throw new FlightServiceException( "El tipo de asiento es obligatorio", HttpStatus.BAD_REQUEST.value());
        }

        if (flightDTO.getPrice() == null || flightDTO.getPrice() < 0) {
            throw new HotelServiceException("El precio debe ser un número positivo", HttpStatus.BAD_REQUEST.value());
        }
    }

    public static void validateDate(LocalDate dateFrom){
        if (dateFrom.isBefore(LocalDate.now())) {
            throw new FlightServiceException("Las fecha debe ser futura", HttpStatus.BAD_REQUEST.value());
        }
    }

    public static void validateObjectDate(FlightDTO flightDTO){
            validateDate(flightDTO.getDateFrom());
        }

    public static void validateNonEmptyList(List<Flight> list) {
        if (list.isEmpty()) {
            throw new FlightServiceException("No hay vuelos disponibles", HttpStatus.NOT_FOUND.value());
        }
    }

    public static void validateNonBooked(Flight flight, Long id){
        if (flight.isBooked()) {
            throw new FlightServiceException("vuelo " + id +" reservado, si desea eliminar/modificar contacte con supervisión", HttpStatus.NOT_FOUND.value());
        }
    }

    public static void validateAvailability(Flight flight, Long id) {
        if (!flight.isAvailable()) {
            throw new HotelServiceException("Vuelo " + id + " eliminado si desea recuperarlo contacte con supervisión", HttpStatus.NOT_FOUND.value());
        }
    }
}
