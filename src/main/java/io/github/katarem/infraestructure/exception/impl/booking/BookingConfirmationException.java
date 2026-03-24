package io.github.katarem.infraestructure.exception.impl.booking;

import io.github.katarem.infraestructure.exception.base.DomainConflictException;

public class BookingConfirmationException extends DomainConflictException {
    public BookingConfirmationException() {
        super("Booking", "Booking cannot be confirmed, only pending bookings can be confirmed");
    }
}
