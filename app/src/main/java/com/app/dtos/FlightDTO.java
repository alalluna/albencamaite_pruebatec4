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

    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonProperty("date")
    private LocalDate dateFrom; //“dd/mm/yyyy”
}
