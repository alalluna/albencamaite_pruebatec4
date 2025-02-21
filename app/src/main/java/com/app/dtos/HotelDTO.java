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
public class HotelDTO {
    @JsonProperty("hotelCode")
    private String code;

    @JsonProperty("name")
    private String hotel;

    @JsonProperty("place")
    private String city;

    @JsonProperty("roomType")
    private String typeRoom;

    @JsonProperty("roomPrice")
    private Double price;

    @JsonProperty("disponibilityDateFrom")
    private String dateFrom;//“dd/mm/yyyy”

    @JsonProperty("disponibilityDateTo")
    private String dateTo;//“dd/mm/yyyy”

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
