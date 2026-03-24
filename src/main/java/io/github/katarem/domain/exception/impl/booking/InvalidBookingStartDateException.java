package io.github.katarem.domain.exception.impl.booking;

import io.github.katarem.domain.exception.base.DomainBusinessException;

public class InvalidBookingStartDateException extends DomainBusinessException {
    public InvalidBookingStartDateException() {
        super("Booking", "Invalid start date");
    }
}
