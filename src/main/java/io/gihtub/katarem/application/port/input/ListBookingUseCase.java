package io.gihtub.katarem.application.port.input;

import io.gihtub.katarem.domain.criteria.BookingCriteria;
import io.gihtub.katarem.domain.criteria.PageCriteria;
import io.gihtub.katarem.domain.model.BookingList;

import java.util.Set;

public interface ListBookingUseCase {
    BookingList listBookings(BookingCriteria bookingCriteria, PageCriteria pageCriteria);
}
