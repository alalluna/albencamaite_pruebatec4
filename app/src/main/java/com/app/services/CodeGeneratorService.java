package com.app.services;

import java.util.Random;

public class CodeGeneratorService {

    public static String generateFlightCode(String origin, String destination, String seatType) {
        String originCode = getFirstTwoLetters(origin);
        String destinationCode = getFirstTwoLetters(destination);
        int seatTypeNumber = getAlphabetPosition(seatType.charAt(0));
        int randomNumbers = new Random().nextInt(900) + 100; // Genera un número entre 100 y 999

        return originCode + destinationCode + "-" + seatTypeNumber + randomNumbers;
    }

    public static String generateHotelCode(String hotelName, String city, String roomType) {
        String hotelCode = getFirstTwoLetters(hotelName);
        String cityCode = getFirstTwoLetters(city);
        int roomTypeNumber = getAlphabetPosition(roomType.charAt(0));
        int randomNumbers = new Random().nextInt(900) + 100; // Genera un número entre 100 y 999

        return hotelCode + cityCode + "-" + roomTypeNumber + randomNumbers;
    }

    private static String getFirstTwoLetters(String text) {
        return text.length() >= 2 ? text.substring(0, 2).toUpperCase() : text.toUpperCase();
    }

    private static int getAlphabetPosition(char letter) {
        return Character.toUpperCase(letter) - 'A' + 1; // Convierte 'A' en 1, 'B' en 2, etc.
    }
}
