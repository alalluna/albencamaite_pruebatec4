package com.app.services;

import com.app.dtos.ErrorDTO;
import lombok.Getter;

@Getter
public class FlightServiceException extends RuntimeException{
    private final ErrorDTO error;

    public FlightServiceException(String message,int status) {
        super(message);
        this.error = new ErrorDTO(message, status);
    }
}
