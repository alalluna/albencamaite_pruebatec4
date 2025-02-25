package com.app.services;

import org.springframework.http.HttpStatus;

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

    public static void validateDates(LocalDate dateFrom, LocalDate dateTo) {
        if (dateTo != null && dateFrom.isAfter(dateTo)) {
            throw new DateException("La fecha de inicio no puede ser posterior a la fecha de fin", HttpStatus.BAD_REQUEST.value());
        }
        if (dateFrom.isBefore(LocalDate.now()) || (dateTo != null && dateTo.isBefore(LocalDate.now()))) {
            throw new DateException("Las fechas deben ser futuras", HttpStatus.BAD_REQUEST.value());
        }
    }
}

