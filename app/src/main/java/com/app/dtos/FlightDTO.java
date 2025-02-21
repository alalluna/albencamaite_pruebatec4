package com.app.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightDTO {
    @JsonProperty("flightNumber")
    private String code;

    @JsonProperty("name")
    private String flightName;

    @JsonProperty("origin")
    private String cityFrom;

    @JsonProperty("destination")
    private String cityDestination;

    @JsonProperty("seatType")
    private String typeOfSeat;

    @JsonProperty("flightPrice")
    private Double price;

    @JsonProperty("date")
    private String dateFrom; //“dd/mm/yyyy”
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
