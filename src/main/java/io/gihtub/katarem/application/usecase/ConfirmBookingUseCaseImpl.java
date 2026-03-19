package io.gihtub.katarem.application.usecase;

import io.gihtub.katarem.application.port.input.ConfirmBookingUseCase;
import io.gihtub.katarem.application.port.output.BookingOutputPort;
import io.gihtub.katarem.domain.model.Booking;
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
