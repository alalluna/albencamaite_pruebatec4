package com.app.services;

import com.app.dtos.FlightBookingDTO;
import com.app.dtos.FlightDTO;
import com.app.entities.Flight;
import com.app.repositories.FlightRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FlightService implements FlightServiceInterface{

    @Autowired
    FlightRepositoryInterface repository;

    @Override
    public List<FlightDTO> list() {
        List<Flight> listOfElements = repository.findAll().stream()
                .filter(Flight::isAvailable)
                .filter(flight-> !flight.isBooked())
                .toList();

        if (listOfElements.isEmpty()) {
            throw new FlightServiceException("No hay vuelos disponibles", HttpStatus.NOT_FOUND.value());
        }

        return listOfElements.stream().map(this::mapToDTO).toList();
    }

    @Override
    public FlightDTO findOne(Long id) {
        Optional<Flight> flightOptional = repository.findById(id);

        if (flightOptional.isEmpty()) {
            throw new FlightServiceException("Vuelo " + id + " no encontrado", HttpStatus.NOT_FOUND.value());
        }

        Flight flight = flightOptional.get();

        if (!flight.isAvailable()) {
            throw new FlightServiceException("Vuelo " + id + " eliminado, selecciona otro", HttpStatus.NOT_FOUND.value());
        }

        if (flight.isBooked()) {
            throw new FlightServiceException("Vuelo " + id + " no disponible actualmente", HttpStatus.NOT_FOUND.value());
        }

        return mapToDTO(flight);
    }

    @Override
    public FlightDTO create(FlightDTO flightDTO) {
        FlightServiceValidation.validateDTO(flightDTO);
        FlightServiceValidation.validateObjectDate(flightDTO);
        Flight flight = mapToEntity(flightDTO);
        validateNonDuplicateFlight(flight);
        Flight savedObject = repository.save(flight);
        return mapToDTO(savedObject);
    }

    @Override
    public FlightDTO update(Long id, FlightDTO flightDTO) {
        return null;
    }

    @Override
    public void delete(Long id) {
        Flight flight = repository.findById(id)
                .orElseThrow(() -> new FlightServiceException("Vuelo " + id + " no encontrado", HttpStatus.NOT_FOUND.value()));

        if(!flight.isAvailable()){
            throw new FlightServiceException("Vuelo " + id + " eliminado.", HttpStatus.BAD_REQUEST.value());
        }

        flight.setAvailable(false);
        repository.save(flight);
    }

    @Override
    public List<FlightDTO> filterFlights(String dateFrom, String dateTo, String origin, String destination) {
        return List.of();
    }

    @Override
    public FlightBookingDTO createBooking(FlightBookingDTO flightBookingDTO) {
        return null;
    }

    @Override
    public void validateNonDuplicateFlight(Flight flight) {

    }

    @Override
    public List<Flight> getTrueList() {
        return List.of();
    }

    @Override
    public boolean compareObjects(Flight objectOne, Flight objectTwo) {
        return false;
    }

    @Override
    public void updateHotelData(Flight flight, FlightDTO flightDTO) {

    }

    @Override
    public FlightDTO mapToDTO(Flight flight) {
        return new FlightDTO(
                flight.getCode(),
                flight.getFlightName(),
                flight.getCityFrom(),
                flight.getCityDestination(),
                flight.getTypeOfSeat(),
                flight.getPrice(),
                flight.getDateFrom()
        );
    }

    @Override
    public Flight mapToEntity(FlightDTO flightDTO) {
        return new Flight(
                null,
                flightDTO.getCode(),
                flightDTO.getFlightName(),
                flightDTO.getCityFrom(),
                flightDTO.getCityDestination(),
                flightDTO.getTypeOfSeat(),
                flightDTO.getPrice(),
                flightDTO.getDateFrom(),
                false,
                true,
                new ArrayList<>()

        );
    }
}
