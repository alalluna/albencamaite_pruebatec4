package com.app.services;

import com.app.dtos.ErrorDTO;

public class FlightServiceException extends RuntimeException{
    private final ErrorDTO error;
    public FlightServiceException(String message,int code) {
        super(message);
        this.error = new ErrorDTO(message, code);
    }
    public FlightServiceException(String message, ErrorDTO error) {
        super(message);
        this.error = error;
    }
}
