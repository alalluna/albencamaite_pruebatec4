package com.app.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private String origin;
    private String destination;
    private String seatType;
    private Integer prize;
    private LocalDate dateFrom;

    //este es para las reservas, si esta reservado se verá si,
    // si esta dispobile se vera no, ejercutar una reerva sera buscar por id y marcarlo como reservado
    private boolean isBooked;

    //este es para elimnar o no. si esta habilitado se ve en las listas y busquedas, sino permanece oculto
    private boolean isAvailable;

    //lista de pasajeros

}
