package io.github.katarem.application.port.output;

import io.github.katarem.domain.criteria.BookingCriteria;
import io.github.katarem.domain.criteria.PageCriteria;
import io.github.katarem.domain.model.Booking;
import io.github.katarem.domain.model.BookingList;

import java.util.Set;
import java.util.UUID;

public interface BookingOutputPort {
    Booking getBooking(UUID id);
    Booking upsertBooking(Booking booking);

    Set<Booking> getAllBookingsByRoomId(Integer roomId);

    BookingList getBookings(BookingCriteria bookingCriteria, PageCriteria pageCriteria);

}
