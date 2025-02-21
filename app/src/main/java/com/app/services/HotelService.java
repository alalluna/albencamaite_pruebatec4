package com.app.services;

import com.app.dtos.HotelDTO;
import com.app.entities.Hotel;
import com.app.repositories.HotelRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HotelService implements HotelServiceInterface {

    @Autowired
    HotelRepositoryInterface repository;

    @Override
    public List<HotelDTO> list() {
        List<Hotel> listOfElements = repository.findAll().stream()
                .filter(Hotel::isAvailable)
                .toList();

        return listOfElements.stream().map(this::mapToDTO).toList();
    }

    @Override
    public HotelDTO findOne(Long id) {
        return repository.findById(id)
                .map(this::mapToDTO)
              .orElseThrow(() -> new HotelServiceException("Hotel " + id + " no encontrado", HttpStatus.NOT_FOUND.value()));
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
