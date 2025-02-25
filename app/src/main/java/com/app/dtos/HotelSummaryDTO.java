package com.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelSummaryDTO {
    private String hotelName;
    private String city;
    private List<String> roomCodes;
    private List<String> roomTypes;
}
