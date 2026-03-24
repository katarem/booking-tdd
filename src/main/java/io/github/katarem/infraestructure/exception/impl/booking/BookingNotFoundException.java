package io.github.katarem.infraestructure.exception.impl.booking;

import io.github.katarem.infraestructure.exception.base.DomainNotFoundException;

import java.util.UUID;


public class BookingNotFoundException extends DomainNotFoundException {

    public BookingNotFoundException(UUID bookingId) {
        super("Booking", bookingId.toString());
    }
}
