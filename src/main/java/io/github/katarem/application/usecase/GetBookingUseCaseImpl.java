package io.github.katarem.application.usecase;

import io.github.katarem.application.port.input.GetBookingUseCase;
import io.github.katarem.application.port.output.BookingOutputPort;
import io.github.katarem.domain.model.Booking;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetBookingUseCaseImpl implements GetBookingUseCase {

    private final BookingOutputPort outputPort;

    @Override
    public Booking getBooking(UUID bookingId) {
        return outputPort.getBooking(bookingId);
    }
}
