package io.github.katarem.application.port.input;

import io.github.katarem.domain.model.Booking;

import java.util.UUID;

public interface ConfirmBookingUseCase {
    Booking confirmBooking(UUID bookingId);
}
