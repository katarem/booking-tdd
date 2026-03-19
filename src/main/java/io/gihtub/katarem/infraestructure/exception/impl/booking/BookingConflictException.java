package io.gihtub.katarem.infraestructure.exception.impl.booking;

import io.gihtub.katarem.infraestructure.exception.base.DomainConflictException;

public class BookingConflictException extends DomainConflictException {
    public BookingConflictException() {
        super("Booking", "Booking overlaps with existing bookings");
    }
}
