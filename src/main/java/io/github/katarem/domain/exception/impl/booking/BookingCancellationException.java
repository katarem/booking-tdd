package io.github.katarem.domain.exception.impl.booking;

import io.github.katarem.domain.exception.base.DomainConflictException;

public class BookingCancellationException extends DomainConflictException {
    public BookingCancellationException() {
        super("Booking", "Booking cannot be cancel cancelled bookings!");
    }
}
