package com.app.services;

import com.app.dtos.FlightBookingDTO;
import com.app.dtos.FlightDTO;
import com.app.dtos.UserDTO;
import com.app.entities.*;
import com.app.repositories.FlightRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FlightService implements FlightServiceInterface{

    @Autowired
    FlightRepositoryInterface repository;

    @Override
    public List<FlightDTO> list() {
    List<Flight> listOfElements = getTrueList();
        FlightServiceValidations.validateNonEmptyList(listOfElements);
        return listOfElements.stream().map(this::mapToDTO).toList();
    }

    @Override
    public FlightDTO findOne(Long id) {
        Optional<Flight> flightOptional = repository.findById(id);

        if (flightOptional.isEmpty()) {
            throw new FlightServiceException("Vuelo " + id + " no encontrado", HttpStatus.NOT_FOUND.value());
        }
        Flight flight = flightOptional.get();
        FlightServiceValidations.validateAvailability(flight,id);
        FlightServiceValidations.validateNonBooked(flight,id);
        return mapToDTO(flight);
    }

    @Override
    public FlightDTO create(FlightDTO flightDTO) {
        FlightServiceValidations.validateDTO(flightDTO);
        FlightServiceValidations.validateObjectDate(flightDTO);
        Flight flight = mapToEntity(flightDTO);
        flight.setCode(CodeGeneratorService.generateFlightCode(flight.getCityFrom(), flight.getCityDestination(), flight.getTypeOfSeat()));
        validateNonDuplicateFlight(flight);
        Flight savedObject = repository.save(flight);
        return mapToDTO(savedObject);
    }

    @Override
    public FlightDTO update(Long id, FlightDTO flightDTO) {
        Flight flight = repository.findById(id)
                        .orElseThrow(() -> new FlightServiceException("Vuelo " + id +" no encontrado", HttpStatus.NOT_FOUND.value()));

        FlightServiceValidations.validateAvailability(flight, id);
        FlightServiceValidations.validateNonBooked(flight, id);
        updateFlightData(flight, flightDTO);

        flight.setCode(CodeGeneratorService.generateFlightCode(flight.getCityFrom(), flight.getCityDestination(), flight.getTypeOfSeat()));
        FlightServiceValidations.validateObjectDate(flightDTO);
        Flight updatedObject = repository.save(flight);
        return mapToDTO(updatedObject);
    }

    @Override
    public void delete(Long id) {
        Flight flight = repository.findById(id)
                .orElseThrow(() -> new FlightServiceException("Vuelo " + id + " no encontrado", HttpStatus.NOT_FOUND.value()));
        FlightServiceValidations.validateAvailability(flight,id);
        FlightServiceValidations.validateNonBooked(flight,id);
        flight.setAvailable(false);
        repository.save(flight);
    }

    @Override
    public List<FlightDTO> filterFlights(String dateFrom, String dateTo, String origin, String destination) {
        LocalDate firstDate = DateUtilService.parseDate(dateFrom);
        LocalDate secondDate = (dateTo != null && !dateTo.isBlank()) ? DateUtilService.parseDate(dateTo) : null;
        List<Flight> flights = new ArrayList<>(getTrueList().stream()
                .filter(flight -> flight.getCityFrom().equalsIgnoreCase(origin))
                .filter(flight -> flight.getCityDestination().equalsIgnoreCase(destination))
                .filter(flight -> flight.getDateFrom().isEqual(firstDate))
                .toList());

        if (secondDate != null) {
            List<Flight> returnFlights = getTrueList().stream()
                    .filter(flight -> flight.getCityFrom().equalsIgnoreCase(destination))
                    .filter(flight -> flight.getCityDestination().equalsIgnoreCase(origin))
                    .filter(flight -> flight.getDateFrom().isEqual(secondDate))
                    .toList();
            flights.addAll(returnFlights);
        }

        FlightServiceValidations.validateNonEmptyList(flights);
        return flights.stream().map(this::mapToDTO).toList();
    }

    @Override
    public FlightBookingDTO createBooking(FlightBookingDTO flightBookingDTO) {
        Flight flight = findAvailableFlight(flightBookingDTO);
        flight.setBooked(true);

        FlightBooking newBooking = createNewBooking(flightBookingDTO, flight);
        List<User> passengers = createUserList(flightBookingDTO, newBooking);

        newBooking.setPassengers(passengers);
        flight.getBookings().add(newBooking);
        repository.save(flight);

        return buildBookingDTO(newBooking, flight, passengers);
    }
    @Override
    public Flight findAvailableFlight(FlightBookingDTO flightBookingDTO) {
        return getTrueList().stream()
                .filter(flight -> flight.getCityFrom().equalsIgnoreCase(flightBookingDTO.getCityFrom()))
                .filter ( flight ->flight.getCityDestination().equalsIgnoreCase(flightBookingDTO.getCityDestination()))
                .filter(flight -> flight.getDateFrom().isEqual(flightBookingDTO.getDateFrom()))
                .findFirst()
                .orElseThrow(() -> new FlightServiceException( "No hay vuelos disponibles con los criterios especificados", HttpStatus.NOT_FOUND.value()
                ));
    }
    @Override
    public FlightBooking createNewBooking(FlightBookingDTO flightBookingDTO, Flight flight) {
        FlightBooking newBooking = new FlightBooking();
        newBooking.setFlight(flight);
        newBooking.setFlightDate(flightBookingDTO.getDateFrom());
        newBooking.setSeatType(flightBookingDTO.getTypeOfSeat());
        return newBooking;
    }
    @Override
    public List<User> createUserList(FlightBookingDTO flightBookingDTO, FlightBooking reserve) {
        return flightBookingDTO.getClients().stream()
                .map(dto -> new User(null, dto.getCompleteName(), dto.getContact(), null, reserve))
                .toList();
    }
    @Override
    public FlightBookingDTO buildBookingDTO(FlightBooking reserve, Flight flight, List<User> clients) {
        List<UserDTO> clientsDTOs = clients.stream()
                .map(user -> new UserDTO(user.getCompleteName(), user.getContact()))
                .toList();

        return new FlightBookingDTO(
                reserve.getFlightDate(),
                flight.getCityFrom(),
                flight.getCityDestination(),
                flight.getCode(),
                clients.size(),
                reserve.getSeatType(),
                clientsDTOs
        );
    }

    @Override
    public void validateNonDuplicateFlight(Flight flight) {
        boolean exist = repository.findAll().stream()
                .anyMatch(oldObject ->compareObjects(oldObject, flight));
        if(exist){
            throw new FlightServiceException("Ya existe un vuelo igual, hable con supervisión", HttpStatus.CONFLICT.value());
        }
    }

    @Override
    public List<Flight> getTrueList() {
        return repository.findAll().stream()
                .filter(Flight::isAvailable)
                .filter(flight -> !flight.isBooked())
                .toList();
    }

    @Override
    public boolean compareObjects(Flight objectOne, Flight objectTwo) {
        return objectOne.getFlightName().equalsIgnoreCase(objectTwo.getFlightName()) &&
                objectOne.getCityFrom().equalsIgnoreCase(objectTwo.getCityFrom()) &&
                objectOne.getCityDestination().equalsIgnoreCase(objectTwo.getCityDestination()) &&
                objectOne.getTypeOfSeat().equalsIgnoreCase(objectTwo.getTypeOfSeat()) &&
                objectOne.getPrice().equals(objectTwo.getPrice()) &&
                objectOne.getDateFrom().isEqual(objectTwo.getDateFrom());
    }

    @Override
    public void updateFlightData(Flight flight, FlightDTO flightDTO) {
        flight.setCode(flightDTO.getCode());
        flight.setFlightName(flightDTO.getFlightName());
        flight.setCityFrom(flightDTO.getCityFrom());
        flight.setCityDestination(flightDTO.getCityDestination());
        flight.setTypeOfSeat(flight.getTypeOfSeat());
        flight.setPrice(flightDTO.getPrice());
        flight.setDateFrom(flightDTO.getDateFrom());
        flight.setBooked(flightDTO.isBooked());

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
                flight.getDateFrom(),
                flight.isBooked()
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
