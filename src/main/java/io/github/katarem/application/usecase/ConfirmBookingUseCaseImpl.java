package io.github.katarem.application.usecase;

import io.github.katarem.application.port.input.ConfirmBookingUseCase;
import io.github.katarem.application.port.output.BookingOutputPort;
import io.github.katarem.domain.model.Booking;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConfirmBookingUseCaseImpl implements ConfirmBookingUseCase {

    private final BookingOutputPort outputPort;
    @Override
    public Booking confirmBooking(UUID bookingId) {
        var booking = outputPort.getBooking(bookingId);
        booking.confirm();
        return outputPort.upsertBooking(booking);
    }
}
