package io.gihtub.katarem.infraestructure.exception;

import java.util.UUID;


public class BookingNotFoundException extends BookingBaseException {

    public BookingNotFoundException(UUID bookingId) {
        super("BOOKING_NOT_FOUND", "Booking " + bookingId + " not found.");
    }
}
