package com.app.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonBackReference
    private Flight flight;

    // Usuario que hizo la reserva
    @ManyToOne
    @JsonBackReference
    private User employee;

    //lista de pasajeros
    @OneToMany(mappedBy = "FlightBooking",cascade = CascadeType.ALL, orphanRemoval = true,  fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<User> clients = new ArrayList<>();

    private LocalDate departure;
    private boolean isBooked;
}
