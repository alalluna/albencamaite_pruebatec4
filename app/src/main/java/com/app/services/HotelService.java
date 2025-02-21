package com.app.services;

import com.app.dtos.HotelDTO;
import com.app.entities.Hotel;
import com.app.repositories.HotelRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class HotelService implements HotelServiceInterface {

    @Autowired
    HotelRepositoryInterface repository;

    @Override
    public List<HotelDTO> list() {
        List<Hotel> listOfElements = repository.findAll().stream()
                .filter(Hotel::isAvailable)
                .filter(hotel-> !hotel.isBooked())
                .toList();
        //validar si no hay datos para que no salga un lista vacia
        if (listOfElements.isEmpty()) {
            throw new HotelServiceException("No hay habitaciones disponibles", HttpStatus.NOT_FOUND.value());
        }

        return listOfElements.stream().map(this::mapToDTO).toList();
    }

    @Override
    public HotelDTO findOne(Long id) {
        Optional<Hotel> hotelOptional = repository.findById(id);

        //validar si no hay datos, si no está habilitado o no esta disponible

        if (hotelOptional.isEmpty()) {
            throw new HotelServiceException("Hotel " + id + " no encontrado", HttpStatus.NOT_FOUND.value());
        }

        Hotel hotel = hotelOptional.get();

        if (!hotel.isAvailable()) {
            throw new HotelServiceException("Hotel " + id + " no habilitado", HttpStatus.NOT_FOUND.value());
        }

        if (hotel.isBooked()) {
            throw new HotelServiceException("Esta habitación no está disponible", HttpStatus.NOT_FOUND.value());
        }

        return mapToDTO(hotel);
    }

    @Override
    public HotelDTO create(HotelDTO hotelDTO) {
        return null;
    }

    @Override
    public HotelDTO update(Long id, HotelDTO hotelDTO) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

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
}
