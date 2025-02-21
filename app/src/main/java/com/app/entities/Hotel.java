package com.app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String hotelName;
    private String city;
    private String typeRoom;
    private Double price;
    private LocalDate from;
    private LocalDate to;

    //este es para las reservas, si esta reservado se verá si,
    // si esta dispobile se vera no, ejecutar una reerva sera buscar por id
    // y marcarlo como reservado
    private boolean isBooked;

    //este es para elimnar o no. si esta habilitado se ve en las listas y busquedas,
    // sino permanece oculto
    private boolean isAvailable;

    //aqui no establezco relacion, tampoco en use y tampoco en flight, lo harén en las reservas
}
