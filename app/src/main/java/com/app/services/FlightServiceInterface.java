package com.app.services;

import com.app.dtos.FlightBookingDTO;
import com.app.dtos.FlightDTO;
import com.app.entities.Flight;
import com.app.entities.FlightBooking;
import com.app.entities.User;

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
    Flight findAvailableFlight(FlightBookingDTO flightBookingDTO);
    FlightBooking createNewBooking(FlightBookingDTO flightBookingDTO, Flight flight);
    List<User> createUserList(FlightBookingDTO flightBookingDTO, FlightBooking reserve);
    FlightBookingDTO buildBookingDTO(FlightBooking reserve, Flight flight, List<User> clients);

    //conversores
    FlightDTO mapToDTO(Flight flight);
    Flight mapToEntity(FlightDTO flightDTO);

}
