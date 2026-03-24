package io.github.katarem.domain.exception.impl.booking;

import io.github.katarem.domain.exception.base.DomainConflictException;

public class BookingCancellationTimeExpiredException extends DomainConflictException {
    public BookingCancellationTimeExpiredException() {
        super("Booking", "Booking cannot be cancelled, it can be only cancelled until 15 minutes before its start");
    }
}
