package io.github.katarem.domain.exception.impl.booking;

import io.github.katarem.domain.exception.base.DomainBusinessException;

public class InvalidBookingPeriod extends DomainBusinessException {
  public InvalidBookingPeriod() {
    super("Booking", "Bookings can only last from 30 minutes to 8 hours");
  }
}
