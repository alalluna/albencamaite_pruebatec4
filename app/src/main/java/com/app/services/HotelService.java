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
        //validar si no hay datos para que no salga un lista vacia
        validateNonEmptyList(listOfElements);

        return listOfElements.stream().map(this::mapToDTO).toList();
    }

    @Override
    public HotelDTO findOne(Long id) {
        Optional< Hotel> hotelOptional = repository.findById(id);

        //validar si no hay datos, si no está habilitado o no esta disponible
        if (hotelOptional.isEmpty()) {
            throw new HotelServiceException("Habitación " + id + " no encontrada", HttpStatus.NOT_FOUND.value());
        }

        Hotel hotel = hotelOptional.get();
        validateAvailability(hotel, id);
        validateNonBooked(hotel, id);

        return mapToDTO(hotel);

    }

    @Override
    public HotelDTO create(HotelDTO hotelDTO) {
        validateDTO(hotelDTO);
        validateDates(hotelDTO);

        Hotel hotel = mapToEntity(hotelDTO);

        validateNonDuplicateHotel(hotel);
        Hotel savedObject = repository.save(hotel);
        return mapToDTO(savedObject);
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

        validateAvailability(hotel, id);

        hotel.setAvailable(false);
        repository.save(hotel);
    }

    //validaciones
    @Override
    public void validateDTO(HotelDTO hotelDTO) {
        if (hotelDTO.getHotelName() == null || hotelDTO.getHotelName().isBlank()) {
            throw new HotelServiceException("El nombre del hotel es obligatorio", HttpStatus.BAD_REQUEST.value());
        }
        if (hotelDTO.getCity() == null || hotelDTO.getCity().isBlank()) {
            throw new HotelServiceException("La ciudad es obligatoria", HttpStatus.BAD_REQUEST.value());
        }
        if (hotelDTO.getPrice() == null || hotelDTO.getPrice() < 0) {
            throw new HotelServiceException("El precio debe ser un número positivo", HttpStatus.BAD_REQUEST.value());
        }

    }

    public void validateDates(HotelDTO hotelDTO){
        if (hotelDTO.getDateFrom().isAfter(hotelDTO.getDateTo())) {
            throw new HotelServiceException("La fecha de inicio debe ser antes de la fecha de fin", HttpStatus.BAD_REQUEST.value());
        }
        if (hotelDTO.getDateFrom().isBefore(LocalDate.now()) || hotelDTO.getDateTo().isBefore(LocalDate.now())) {
            throw new HotelServiceException("La fechas deben ser futuras", HttpStatus.BAD_REQUEST.value());
        }
    }

    @Override
    public void validateNonEmptyList(List<Hotel> list) {
        if (list.isEmpty()) {
            throw new HotelServiceException("No hay habitaciones disponibles", HttpStatus.NOT_FOUND.value());
        }
    }

    @Override
    public void validateNonBooked(Hotel hotel,Long id){
        if (hotel.isBooked()) {
            throw new HotelServiceException("Habitación " + id +" no disponible actualmente", HttpStatus.NOT_FOUND.value());
        }
    }

    @Override
    public void validateAvailability(Hotel hotel, Long id) {
        if (!hotel.isAvailable()) {
            throw new HotelServiceException("Habitación " + id + " eliminada si desea recuperarla contacte con supervisión", HttpStatus.NOT_FOUND.value());
        }
    }

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
                hotelDTO.isBooked(),
                true, // pongo el boleano Available por defecto
                new ArrayList<>()
        );
    }
}
