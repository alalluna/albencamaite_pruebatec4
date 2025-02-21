package com.app.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelDTO {
    @JsonProperty("hotelCode")
    private String code;

    @JsonProperty("name")
    private String hotelName;

    @JsonProperty("place")
    private String city;

    @JsonProperty("roomType")
    private String typeRoom;

    @JsonProperty("roomPrice")
    private Double price;

    @JsonProperty("disponibilityDateFrom")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateFrom;//“dd/mm/yyyy”

    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonProperty("disponibilityDateTo")
    private LocalDate dateTo;//“dd/mm/yyyy”

    //si empieza por is necesita la etiqueta aunque sea el mismo nombre
    //jacson hace esto
    @JsonProperty("isBooked")
    private boolean isBooked;
}
//
//{
//        "hotelCode": “String”,
//        "name": “String”,
//        "place": “String”,
//        "roomType": “String”,
//        "roomPrice": Double,
//        "disponibilityDateFrom": “dd/mm/yyyy”,
//        "disponibilityDateTo": “dd/mm/yyyy”,
//        "isBooked": “boolean”
//        }
