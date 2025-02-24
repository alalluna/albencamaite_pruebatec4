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
    //cambio en los formatos, porque da problemas
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy")
    private LocalDate dateFrom;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy")
    @JsonProperty("disponibilityDateTo")
    private LocalDate dateTo;

    @JsonProperty("isBooked")
    private boolean Booked;
}