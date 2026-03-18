package io.gihtub.katarem.infraestructure.exception.impl.booking;

import io.gihtub.katarem.infraestructure.exception.base.DomainBusinessException;

public class InvalidBookingDate extends DomainBusinessException {
    public InvalidBookingDate() {
        super("Booking", "Bookings are only available between 08:00 and 20:00");
    }
}
