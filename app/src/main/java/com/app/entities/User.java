package com.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String completeName;

    private String contact;

    //cambio en la relacion
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "hotel_booking_id")
    private HotelBooking booking;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "flight_booking_id")
    private FlightBooking reserve;
}
