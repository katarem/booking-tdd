package io.gihtub.katarem.infraestructure.exception.impl.booking;

import io.gihtub.katarem.infraestructure.exception.base.DomainNotFoundException;

import java.util.UUID;


public class BookingNotFoundException extends DomainNotFoundException {

    public BookingNotFoundException(UUID bookingId) {
        super("Booking", bookingId.toString());
    }
}
