package io.github.katarem.application.port.input;

import io.github.katarem.domain.criteria.BookingCriteria;
import io.github.katarem.domain.criteria.PageCriteria;
import io.github.katarem.domain.model.BookingList;

public interface ListBookingUseCase {
    BookingList listBookings(BookingCriteria bookingCriteria, PageCriteria pageCriteria);
}
