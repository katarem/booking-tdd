package io.gihtub.katarem.infraestructure.exception.impl.booking;

import io.gihtub.katarem.infraestructure.exception.base.DomainBusinessException;

public class InvalidBookingStartDateException extends DomainBusinessException {
    public InvalidBookingStartDateException() {
        super("Booking", "Invalid start date");
    }
}
