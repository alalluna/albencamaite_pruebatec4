package com.app.services;

import com.app.dtos.FlightBookingDTO;
import com.app.dtos.FlightDTO;
import com.app.entities.Flight;

import java.util.List;

public interface FlightServiceInterface {
    List<FlightDTO> list();
    FlightDTO findOne(Long id);
    FlightDTO create(FlightDTO flightDTO);
    FlightDTO update(Long id,  FlightDTO flightDTO);
    void delete(Long id);
    List<FlightDTO> filterFlights( String dateFrom, String dateTo, String origin, String destination);
    FlightBookingDTO createBooking(FlightBookingDTO flightBookingDTO);

    //auxiliares
    void validateNonDuplicateFlight(Flight flight);
    List<Flight>getTrueList();
    boolean compareObjects(Flight objectOne, Flight objectTwo);
    void updateFlightData(Flight flight,FlightDTO flightDTO);

    //conversores
    FlightDTO mapToDTO(Flight flight);
    Flight mapToEntity(FlightDTO flightDTO);

}
