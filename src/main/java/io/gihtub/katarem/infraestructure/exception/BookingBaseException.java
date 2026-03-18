package io.gihtub.katarem.infraestructure.exception;

import lombok.Getter;

@Getter
public class BookingBaseException extends RuntimeException {

    private final String code;

    public BookingBaseException(String code, String message) {
        super(message);
        this.code = code;
    }
}
