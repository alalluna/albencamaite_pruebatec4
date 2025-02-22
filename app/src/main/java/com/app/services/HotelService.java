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
            throw new HotelServiceException("Habitación " + id + " no encontrada", HttpStatus.NOT_FOUND.value());
        }

        Hotel hotel = hotelOptional.get();

        if (!hotel.isAvailable()) {
            throw new HotelServiceException("Habitación " + id + " eliminada, selecciona otra", HttpStatus.NOT_FOUND.value());
        }

        if (hotel.isBooked()) {
            throw new HotelServiceException("Habitación " + id +" no disponible actualmente", HttpStatus.NOT_FOUND.value());
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
        // si se encuentra si están inhabilitado y si se "elimina" correctamente
        Hotel hotel = repository.findById(id)
                .orElseThrow(() -> new HotelServiceException("Habitación " + id + " no encontrada", HttpStatus.NOT_FOUND.value()));

        if (!hotel.isAvailable()) {
            throw new HotelServiceException("Habitación " + id + " eliminada", HttpStatus.BAD_REQUEST.value());
        }

        hotel.setAvailable(false);
        repository.save(hotel);
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
