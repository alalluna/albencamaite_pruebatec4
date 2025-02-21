package com.app.services;

import com.app.dtos.FlightDTO;
import com.app.entities.Flight;
import com.app.repositories.FlightRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightService  implements FlightServiceInterface{

    @Autowired
    FlightRepositoryInterface repository;


    @Override
    public List<FlightDTO> list() {
        List<Flight> listOfElements = repository.findAll().stream()
                .filter(Flight::isAvailable)
                .filter(flight-> !flight.isBooked())
                .toList();

        if (listOfElements.isEmpty()) {
            throw new FlightServiceException("No hay habitaciones disponibles", HttpStatus.NOT_FOUND.value());
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
            throw new FlightServiceException("Vuelo " + id + " no habilitado", HttpStatus.NOT_FOUND.value());
        }

        if (flight.isBooked()) {
            throw new FlightServiceException("Este vuelo no está disponible", HttpStatus.NOT_FOUND.value());
        }

        return mapToDTO(flight);
    }


    @Override
    public FlightDTO create(FlightDTO flightDTO) {
        return null;
    }

    @Override
    public FlightDTO update(Long id, FlightDTO flightDTO) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public FlightDTO mapToDTO(Flight flight) {
        return new FlightDTO(
                flight.getCode(),
                flight.getFlightName(),
                flight.getCityFrom(),
                flight.getCityDestination(),
                flight.getTypeOfSeat(),
                flight.getPrize(),
                flight.getDateFrom()
        );
    }
}
