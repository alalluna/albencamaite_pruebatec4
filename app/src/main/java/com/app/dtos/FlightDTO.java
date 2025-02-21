package com.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightDTO {
    private String flightNumber;
    private String name;
    private String origin;
    private String destination;
    private String seatType;
    private Double flightPrice;
    private String date; //“dd/mm/yyyy”
}
//{
//        "flightNumber": “String”,
//        "name": “String”,
//        "origin": “String”,
//        "destination": “String”,
//        "seatType": “String”,
//        "flightPrice": Double,
//        "date": “dd/mm/yyyy”,
//        }
