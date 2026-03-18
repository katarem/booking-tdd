package io.gihtub.katarem.infraestructure.exception.impl.booking;

import io.gihtub.katarem.infraestructure.exception.base.DomainBusinessException;

public class BookingConflictException extends DomainBusinessException {
    public BookingConflictException() {
        super("Booking", "Booking overlaps with existing bookings");
    }
}
