package io.github.katarem.application.port.input;

import io.github.katarem.domain.model.Booking;

public interface CreateBookingUseCase {
    Booking createBooking(Booking booking);
}
