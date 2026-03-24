package io.github.katarem.domain.exception.impl.booking;

import io.github.katarem.domain.exception.base.DomainConflictException;

public class BookingConfirmationException extends DomainConflictException {
    public BookingConfirmationException() {
        super("Booking", "Booking cannot be confirmed, only pending bookings can be confirmed");
    }
}
