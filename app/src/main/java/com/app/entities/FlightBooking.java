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
public class FlightBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //vuelo que pertence a esa reserva
    @ManyToOne
    private Flight flight;

    // Usuario que hizo la reserva
    @ManyToOne
    private User bookingUser;

    //lista de pasajeros
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> passengers = new ArrayList<>();

    private LocalDate departure;
    private boolean isBooked;
}
