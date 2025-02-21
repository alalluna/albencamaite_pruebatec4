package com.app.services;

import com.app.dtos.FlightDTO;
import com.app.dtos.HotelDTO;
import com.app.entities.Flight;
import com.app.repositories.FlightRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService  implements FlightServiceInterface{

    @Autowired
    FlightRepositoryInterface repository;


    @Override
    public List<FlightDTO> list() {
        List<Flight> listOfElements = repository.findAll().stream()
                .filter(Flight::isAvailable)
                .toList();
        return listOfElements.stream().map(this::mapToDTO).toList();

    }

    @Override
    public FlightDTO findOne(Long id) {
                return repository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new FlightServiceException("Vuelo " + id + " no encontrado", HttpStatus.NOT_FOUND.value()));
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
