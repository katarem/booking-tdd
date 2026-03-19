package io.gihtub.katarem.application.port.input;

import io.gihtub.katarem.domain.model.Booking;

import java.util.UUID;

public interface ConfirmBookingUseCase {
    Booking confirmBooking(UUID bookingId);
}
