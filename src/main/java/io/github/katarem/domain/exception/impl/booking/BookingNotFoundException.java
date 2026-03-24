package io.github.katarem.domain.exception.impl.booking;

import io.github.katarem.domain.exception.base.DomainNotFoundException;

import java.util.UUID;


public class BookingNotFoundException extends DomainNotFoundException {

    public BookingNotFoundException(UUID bookingId) {
        super("Booking", bookingId.toString());
    }
}
