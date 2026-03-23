package io.gihtub.katarem.application.port.output;

import io.gihtub.katarem.domain.criteria.BookingCriteria;
import io.gihtub.katarem.domain.criteria.PageCriteria;
import io.gihtub.katarem.domain.model.Booking;
import io.gihtub.katarem.domain.model.BookingStatus;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

public interface BookingOutputPort {
    Booking getBooking(UUID id);
    Booking upsertBooking(Booking booking);

    Set<Booking> getAllBookingsByRoomId(Integer roomId);

    Set<Booking> getBookings(BookingCriteria bookingCriteria, PageCriteria pageCriteria);

}
