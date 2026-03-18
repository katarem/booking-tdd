package io.gihtub.katarem.application.port.input;

import io.gihtub.katarem.domain.model.Booking;

public interface CreateBookingUseCase {
    Booking createBooking(Booking booking);
}
