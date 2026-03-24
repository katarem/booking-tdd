package io.github.katarem.domain.exception.impl.booking;

import io.github.katarem.domain.exception.base.DomainConflictException;

public class BookingConflictException extends DomainConflictException {
    public BookingConflictException() {
        super("Booking", "Booking overlaps with existing bookings");
    }
}
