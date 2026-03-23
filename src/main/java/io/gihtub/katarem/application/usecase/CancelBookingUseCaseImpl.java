package io.gihtub.katarem.application.usecase;

import io.gihtub.katarem.application.port.input.CancelBookingUseCase;
import io.gihtub.katarem.application.port.output.BookingOutputPort;
import io.gihtub.katarem.domain.model.Booking;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CancelBookingUseCaseImpl implements CancelBookingUseCase {

    private final BookingOutputPort outputPort;
    @Override
    public Booking cancelBooking(UUID bookingId) {
        Booking booking = outputPort.getBooking(bookingId);
        booking.cancel();
        return booking;
    }
}
