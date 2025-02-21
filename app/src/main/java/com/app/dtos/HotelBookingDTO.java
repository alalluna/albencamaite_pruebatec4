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
public class HotelBookingDTO {
    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonProperty("dateFrom")
    private LocalDate from;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonProperty("dateTo")
    private LocalDate to;

    @JsonProperty("nights")
    private Integer numberOfNights;

    @JsonProperty("place")
    private String city;

    @JsonProperty("hotelCode")
    private String code;

    @JsonProperty("peopleQ")
    private Integer numberOfPeople;

    @JsonProperty("roomType")
    private String typeRoom;

    @JsonProperty("hosts")
    private List<User> guests = new ArrayList<>();

}