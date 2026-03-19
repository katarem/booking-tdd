package io.gihtub.katarem.application.port.output;

import io.gihtub.katarem.domain.model.Booking;

import java.util.Set;
import java.util.UUID;

public interface BookingOutputPort {
    Booking getBooking(UUID id);
    Booking upsertBooking(Booking booking);

    Set<Booking> getAllBookingsByRoomId(Integer roomId);

}
