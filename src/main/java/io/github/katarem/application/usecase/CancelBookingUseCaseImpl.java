package io.github.katarem.application.usecase;

import io.github.katarem.application.port.input.CancelBookingUseCase;
import io.github.katarem.application.port.output.BookingOutputPort;
import io.github.katarem.domain.model.Booking;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CancelBookingUseCaseImpl implements CancelBookingUseCase {

    private final BookingOutputPort outputPort;
    private final Clock clock;
    @Override
    public Booking cancelBooking(UUID bookingId) {
        Booking booking = outputPort.getBooking(bookingId);
        booking.cancel(ZonedDateTime.now(clock));
        return booking;
    }
}
