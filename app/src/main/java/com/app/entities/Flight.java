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

    private boolean isBooked;

    private boolean isAvailable;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    private List<FlightBooking> bookings = new ArrayList<>();
}
