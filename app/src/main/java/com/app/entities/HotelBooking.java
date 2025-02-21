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
public class HotelBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Hotel hotel;

    @ManyToOne
    private User bookingUser; // Usuario que hizo la reserva

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> guests = new ArrayList<>(); // Lista de huéspedes

    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private boolean isBooked;
}

