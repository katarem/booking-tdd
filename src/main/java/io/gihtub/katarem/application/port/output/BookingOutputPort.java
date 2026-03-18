package io.gihtub.katarem.application.port.output;

import io.gihtub.katarem.domain.model.Booking;

import java.util.UUID;

public interface BookingOutputPort {
    Booking getBooking(UUID id);
}
