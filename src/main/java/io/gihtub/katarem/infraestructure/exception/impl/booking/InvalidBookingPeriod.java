package io.gihtub.katarem.infraestructure.exception.impl.booking;

import io.gihtub.katarem.infraestructure.exception.base.DomainBusinessException;

public class InvalidBookingPeriod extends DomainBusinessException {
  public InvalidBookingPeriod() {
    super("Booking", "Bookings can only last from 30 minutes to 8 hours");
  }
}
