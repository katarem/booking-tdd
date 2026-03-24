package io.github.katarem.domain.exception.impl.booking;

import io.github.katarem.domain.exception.base.DomainBusinessException;

public class InvalidBookingDate extends DomainBusinessException {
    public InvalidBookingDate() {
        super("Booking", "Bookings are only available between 08:00 and 20:00");
    }
}
