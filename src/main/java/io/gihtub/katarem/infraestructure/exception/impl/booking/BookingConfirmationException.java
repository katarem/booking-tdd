package io.gihtub.katarem.infraestructure.exception.impl.booking;

import io.gihtub.katarem.infraestructure.exception.base.DomainBusinessException;

public class BookingConfirmationException extends DomainBusinessException {
    public BookingConfirmationException() {
        super("Booking", "Booking cannot be confirmed, only pending bookings can be confirmed");
    }
}
