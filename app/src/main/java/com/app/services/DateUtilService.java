package com.app.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtilService {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // de String a LocalDate
    public static LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido: " + date + ". Usa el formato dd/MM/yyyy");
        }
    }

    // de LocalDate a String
    public static String formatDate(LocalDate date) {
        return date.format(FORMATTER);
    }

    //calcular los dias entre dos fechas
    public static int calculateDaysBetween(LocalDate startDate, LocalDate endDate) {
        int days = 0;
        LocalDate countDate = startDate;

        while (countDate.isBefore(endDate)) {
            countDate = countDate.plusDays(1);
            days++;
        }

        return days;
    }
}

