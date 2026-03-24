package io.github.katarem.infraestructure.exception.impl.booking;

import io.github.katarem.infraestructure.exception.base.DomainConflictException;

public class BookingCancellationException extends DomainConflictException {
    public BookingCancellationException() {
        super("Booking", "Booking cannot be cancelled, only pending bookings can be cancelled");
    }
}
