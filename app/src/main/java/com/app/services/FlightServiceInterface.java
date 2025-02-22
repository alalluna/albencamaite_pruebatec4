package com.app.services;

import com.app.dtos.FlightDTO;
import com.app.entities.Flight;

import java.util.List;

public interface FlightServiceInterface {
    List<FlightDTO> list();
    FlightDTO findOne(Long id);
    FlightDTO create(FlightDTO flightDTO);
    FlightDTO update(Long id,  FlightDTO flightDTO);
    void delete(Long id);

    //conversores
    FlightDTO mapToDTO(Flight flight);
}
