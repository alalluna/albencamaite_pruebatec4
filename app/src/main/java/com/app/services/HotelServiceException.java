package com.app.services;

import com.app.dtos.ErrorDTO;

public class HotelServiceException extends RuntimeException {
    private final ErrorDTO error;

    public HotelServiceException(String message,int code) {
        super(message);
        this.error = new ErrorDTO(message, code);
    }

}
