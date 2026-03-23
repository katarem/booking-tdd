package io.gihtub.katarem.application.port.input;

import io.gihtub.katarem.domain.criteria.BookingCriteria;
import io.gihtub.katarem.domain.criteria.PageCriteria;
import io.gihtub.katarem.domain.model.Booking;

import java.util.Set;

public interface ListBookingUseCase {
    Set<Booking> listBookings(BookingCriteria bookingCriteria, PageCriteria pageCriteria);
}
