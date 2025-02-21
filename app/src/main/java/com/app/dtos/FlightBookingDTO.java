package com.app.dtos;

import com.app.entities.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class FlightBookingDTO {
    @JsonProperty("date")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateFrom;

    @JsonProperty("Origin")
    private String cityFrom;

    @JsonProperty("destination")
    private String cityDestination;

    @JsonProperty("flightCode")
    private String code;

    @JsonProperty("peopleQ")
    private Integer numOfPassengers;

    @JsonProperty("seatType")
    private String typeOfSeat;

    @JsonProperty("passengers")
    private List<User> clients= new ArrayList<>();
}
//{
//        "date" : "12/04/2024",
//        "Origin" : "Buenos Aires"
//        "destination" : "Barcelona",
//        "flightCode" : "BUBA-3369",
//        "peopleQ" : 2,
//        "seatType" : "Economy",
//        "passengers" : [
//        {datos persona 1},
//        {datos persona 2}
//        ]
//        }
