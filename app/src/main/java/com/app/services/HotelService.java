package com.app.services;

import com.app.dtos.HotelDTO;
import com.app.entities.Hotel;
import com.app.repositories.HotelRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HotelService implements HotelServiceInterface {

    @Autowired
    HotelRepositoryInterface repository;

    @Override
    public List<HotelDTO> list() {
        List<Hotel> listOfElements = getTrueList();
        HotelServiceValidations.validateNonEmptyList(listOfElements);
        return listOfElements.stream().map(this::mapToDTO).toList();
    }

    @Override
    public HotelDTO findOne(Long id) {
        Optional< Hotel> hotelOptional = repository.findById(id);

        if (hotelOptional.isEmpty()) {
            throw new HotelServiceException("Habitación " + id + " no encontrada", HttpStatus.NOT_FOUND.value());
        }

        Hotel hotel = hotelOptional.get();
        HotelServiceValidations.validateAvailability(hotel, id);
        HotelServiceValidations.validateNonBooked(hotel, id);
        return mapToDTO(hotel);
    }

    @Override
    public HotelDTO create(HotelDTO hotelDTO) {
        HotelServiceValidations.validateDTO(hotelDTO);
        HotelServiceValidations.validateObjectDates(hotelDTO);
        Hotel hotel = mapToEntity(hotelDTO);
        validateNonDuplicateHotel(hotel);
        Hotel savedObject = repository.save(hotel);
        return mapToDTO(savedObject);
    }

    @Override
    public HotelDTO update(Long id, HotelDTO hotelDTO) {
        Hotel hotel = repository.findById(id)
                .orElseThrow(() -> new HotelServiceException("Habitación " + id +" no encontrada", HttpStatus.NOT_FOUND.value()));

        HotelServiceValidations.validateAvailability(hotel, id);
        HotelServiceValidations.validateNonBooked(hotel, id);
        updateHotelData(hotel, hotelDTO);
        HotelServiceValidations.validateObjectDates(hotelDTO);
        Hotel updatedObject = repository.save(hotel);
        return mapToDTO(updatedObject);
    }

    @Override
    public void delete(Long id) {
        Hotel hotel = repository.findById(id)
                .orElseThrow(() -> new HotelServiceException("Habitación " + id + " no encontrada", HttpStatus.NOT_FOUND.value()));

        HotelServiceValidations.validateAvailability(hotel, id);
        HotelServiceValidations.validateNonBooked(hotel, id);
        hotel.setAvailable(false);
        repository.save(hotel);
    }

    @Override
    public List<HotelDTO> filterHotels(LocalDate dateFrom, LocalDate dateTo, String destination) {
        HotelServiceValidations.validateDates(dateFrom, dateTo);
        List<Hotel> rooms = getTrueList().stream()
                .filter(hotel -> hotel.getCity().equalsIgnoreCase(destination))
                .filter(hotel -> hotel.getDateFrom().isBefore(dateTo) && hotel.getDateTo().isAfter(dateFrom))
                .toList();

        HotelServiceValidations.validateNonEmptyList(rooms);
        return rooms.stream().map(this::mapToDTO).toList();
    }

    //auxiliares
    @Override
    public void validateNonDuplicateHotel(Hotel hotel) {
        boolean exist = repository.findAll().stream()
                .anyMatch(oldObject -> compareObjects(oldObject, hotel));
        if (exist) {
            throw new HotelServiceException("Ya existe una habitación igual, hable con supervisión", HttpStatus.CONFLICT.value());
        }
    }

    @Override
    public boolean compareObjects(Hotel object1, Hotel object2) {
        return object1.getHotelName().equalsIgnoreCase(object2.getHotelName()) &&
                object1.getCity().equalsIgnoreCase(object2.getCity()) &&
                object1.getTypeRoom().equalsIgnoreCase(object2.getTypeRoom()) &&
                object1.getPrice().equals(object2.getPrice()) &&
                object1.getDateFrom().equals(object2.getDateFrom()) &&
                object1.getDateTo().equals(object2.getDateTo());
    }

    @Override
    public List<Hotel> getTrueList(){
        return repository.findAll().stream()
                .filter(Hotel::isAvailable)
                .filter(hotel-> !hotel.isBooked())
                .toList();
    }

    @Override
    public void updateHotelData(Hotel hotel, HotelDTO hotelDTO) {
        hotel.setHotelName(hotelDTO.getHotelName());
        hotel.setCity(hotelDTO.getCity());
        hotel.setTypeRoom(hotelDTO.getTypeRoom());
        hotel.setPrice(hotelDTO.getPrice());
        hotel.setDateFrom(hotelDTO.getDateFrom());
        hotel.setDateTo(hotelDTO.getDateTo());
        hotel.setBooked(hotelDTO.isBooked());
    }
    //conversores
    @Override
    public HotelDTO mapToDTO(Hotel hotel) {
        return new HotelDTO(
                hotel.getCode(),
                hotel.getHotelName(),
                hotel.getCity(),
                hotel.getTypeRoom(),
                hotel.getPrice(),
                hotel.getDateFrom(),
                hotel.getDateTo(),
                hotel.isBooked()
        );
    }

    @Override
    public Hotel mapToEntity(HotelDTO hotelDTO) {
        return new Hotel(
                null,
                hotelDTO.getCode(),
                hotelDTO.getHotelName(),
                hotelDTO.getCity(),
                hotelDTO.getTypeRoom(),
                hotelDTO.getPrice(),
                hotelDTO.getDateFrom(),
                hotelDTO.getDateTo(),
               false,
                true,
                new ArrayList<>()
        );
    }
}