package com.app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String flightName;
    private String cityFrom;
    private String cityDestination;
    private String typeOfSeat;
    private Double prize;
    private LocalDate dateFrom;

    //este es para las reservas, si esta reservado se verá si,
    // si esta dispobile se vera no, ejercutar una reerva sera buscar por id y marcarlo como reservado
    private boolean isBooked;

    //este es para elimnar o no. si esta habilitado se ve en las listas y busquedas, sino permanece oculto
    private boolean isAvailable;

    //lista de pasajeros

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    private List<FlightBooking> bookings = new ArrayList<>();
}
