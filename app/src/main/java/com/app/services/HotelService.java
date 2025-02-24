package com.app.services;

import com.app.dtos.HotelBookingDTO;
import com.app.dtos.HotelDTO;
import com.app.dtos.UserDTO;
import com.app.entities.Hotel;
import com.app.entities.HotelBooking;
import com.app.entities.User;
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
    public List<HotelDTO> filterHotels(String dateFrom, String dateTo, String destination) {
        LocalDate startDate = DateUtilService.parseDate(dateFrom);
        LocalDate endDate = DateUtilService.parseDate(dateTo);
        List<Hotel> rooms = getTrueList().stream()
                .filter(hotel -> hotel.getCity().equalsIgnoreCase(destination))
                .filter(hotel -> hotel.getDateFrom().isBefore(endDate) && hotel.getDateTo().isAfter(startDate))
                .toList();

        HotelServiceValidations.validateNonEmptyList(rooms);
        return rooms.stream().map(this::mapToDTO).toList();
    }

    @Override
    public HotelBookingDTO createBooking(HotelBookingDTO hotelBookingDTO) {
        Hotel hotel = findAvailableHotel(hotelBookingDTO);
        hotel.setBooked(true);

        HotelBooking newBooking = createNewBooking(hotelBookingDTO, hotel);
        List<User> guests = createGuestList(hotelBookingDTO, newBooking);

        newBooking.setHosts(guests);
        hotel.getBookings().add(newBooking);
        repository.save(hotel);

        return buildBookingDTO(newBooking, hotel, guests);
    }

    //auxiliares
    private Hotel findAvailableHotel(HotelBookingDTO hotelBookingDTO) {
        return getTrueList().stream()
                .filter(hotel -> hotel.getCity().equalsIgnoreCase(hotelBookingDTO.getCity()))
                .filter(hotel -> hotel.getTypeRoom().equalsIgnoreCase(hotelBookingDTO.getTypeRoom()))
                .filter(hotel -> hotel.getDateFrom().isBefore(hotelBookingDTO.getEndDate())
                        && hotel.getDateTo().isAfter(hotelBookingDTO.getStartDate()))
                .findFirst()
                .orElseThrow(() -> new HotelServiceException( "No hay habitaciones disponibles con los criterios especificados", HttpStatus.NOT_FOUND.value()
                ));
    }

    private HotelBooking createNewBooking(HotelBookingDTO hotelBookingDTO, Hotel hotel) {
        HotelBooking newBooking = new HotelBooking();
        newBooking.setHotel(hotel);
        newBooking.setCheckInDate(hotelBookingDTO.getStartDate());
        newBooking.setCheckOutDate(hotelBookingDTO.getEndDate());
        newBooking.setNumberOfNights(DateUtilService.calculateDaysBetween(
                hotelBookingDTO.getStartDate(),
                hotelBookingDTO.getEndDate()
        ));
        return newBooking;
    }

    private List<User> createGuestList(HotelBookingDTO hotelBookingDTO, HotelBooking booking) {
        return hotelBookingDTO.getGuests().stream()
                .map(dto -> new User(null, dto.getCompleteName(), dto.getContact(), booking))
                .toList();
    }

    private HotelBookingDTO buildBookingDTO(HotelBooking booking, Hotel hotel, List<User> guests) {
        List<UserDTO> guestDTOs = guests.stream()
                .map(user -> new UserDTO(user.getCompleteName(), user.getContact()))
                .toList();

        return new HotelBookingDTO(
                booking.getCheckInDate(),
                booking.getCheckOutDate(),
                booking.getNumberOfNights(),
                hotel.getCity(),
                hotel.getCode(),
                guests.size(),
                hotel.getTypeRoom(),
                guestDTOs
        );
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
    public boolean compareObjects(Hotel objectOne, Hotel objectTwo) {
        return objectOne.getHotelName().equalsIgnoreCase(objectTwo.getHotelName()) &&
                objectOne.getCity().equalsIgnoreCase(objectTwo.getCity()) &&
                objectOne.getTypeRoom().equalsIgnoreCase(objectTwo.getTypeRoom()) &&
                objectOne.getPrice().equals(objectTwo.getPrice()) &&
                objectOne.getDateFrom().equals(objectTwo.getDateFrom()) &&
                objectOne.getDateTo().equals(objectTwo.getDateTo());
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
        hotel.setCode(hotelDTO.getCode());
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