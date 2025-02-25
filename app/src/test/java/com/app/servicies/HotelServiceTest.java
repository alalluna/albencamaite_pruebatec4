package com.app.servicies;

import com.app.dtos.HotelDTO;
import com.app.entities.Hotel;
import com.app.entities.HotelBooking;
import com.app.repositories.HotelRepositoryInterface;
import com.app.services.HotelService;
import com.app.services.HotelServiceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HotelServiceTest {
    @Mock
    private HotelRepositoryInterface repository;

    @InjectMocks
    private HotelService service;

    @Test
    @DisplayName("Encuentra una lista de habitaciones de hotel, verifica sus datos y el tamaño de la lista")
    void listarHoteles(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<Hotel> habitacionesHotel = List.of(
                new Hotel(1L, "NOMA-0202","Nothing","Madrid","Single",100.00,
                        LocalDate.parse("15/04/2025", formatter),LocalDate.parse("18/04/2025", formatter),false,true,new ArrayList<>()),
                new Hotel(2L,"NOMA-0203","Nothing","Madrid","Double", 150.50,
                        LocalDate.parse("10/04/2025", formatter),LocalDate.parse("15/04/2025", formatter),false,true,new ArrayList<>())
        );

        //define el comportamiento del mock del repo
        when(repository.findAll()).thenReturn(habitacionesHotel);


        List<HotelDTO> habitacionesDisponibles = service.list();

        //verificar los valores del DTO, no los de la entidad
        assertThat(habitacionesDisponibles.get(0).getCode()).isEqualTo("NOMA-0202");
        assertThat(habitacionesDisponibles.get(0).getHotelName()).isEqualTo("Nothing");
        assertThat(habitacionesDisponibles.get(0).getCity()).isEqualTo("Madrid");
        assertThat(habitacionesDisponibles.get(0).getTypeRoom()).isEqualTo("Single");
        assertThat(habitacionesDisponibles.get(0).getPrice()).isEqualTo(100.00);
        assertThat(habitacionesDisponibles.get(0).getDateFrom()).isEqualTo(LocalDate.parse("15/04/2025", formatter));
        assertThat(habitacionesDisponibles.get(0).getDateTo()).isEqualTo(LocalDate.parse("18/04/2025", formatter));
        assertThat(habitacionesDisponibles.get(0).isBooked()).isEqualTo(false);

        assertThat(habitacionesDisponibles.get(1).getCode()).isEqualTo("NOMA-0203");
        assertThat(habitacionesDisponibles.get(1).getHotelName()).isEqualTo("Nothing");
        assertThat(habitacionesDisponibles.get(1).getCity()).isEqualTo("Madrid");
        assertThat(habitacionesDisponibles.get(1).getTypeRoom()).isEqualTo("Double");
        assertThat(habitacionesDisponibles.get(1).getPrice()).isEqualTo(150.50);
        assertThat(habitacionesDisponibles.get(1).getDateFrom()).isEqualTo(LocalDate.parse("10/04/2025", formatter));
        assertThat(habitacionesDisponibles.get(1).getDateTo()).isEqualTo(LocalDate.parse("15/04/2025", formatter));
        assertThat(habitacionesDisponibles.get(1).isBooked()).isEqualTo(false);

        assertThat(habitacionesDisponibles.size()).isEqualTo(2);

    }

    @Test
    @DisplayName("Debe lanzar excepción si no hay hoteles registrados")
    void exceptionEmptyList() {
        // Devuelvo una lista DE HABITACIONES vacia
        when(repository.findAll()).thenReturn(List.of());

        // Verifico que se lanza la excepción esperada
        HotelServiceException exception = assertThrows(HotelServiceException.class, () -> service.list());

        assertEquals("No hay habitaciones disponibles", exception.getMessage());

        //no encuentro otra manera de testear una excepción
        //https://stackoverflow.com/questions/156503/how-do-you-assert-that-a-certain-exception-is-thrown-in-junit-tests
        //@Test
        //void exceptionTesting() {
        //    ArithmeticException exception = assertThrows(ArithmeticException.class, () ->
        //        calculator.divide(1, 0));
        //
        //    assertEquals("/ by zero", exception.getMessage());
    }
}
