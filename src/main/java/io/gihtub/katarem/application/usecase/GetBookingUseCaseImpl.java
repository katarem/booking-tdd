package io.gihtub.katarem.application.usecase;

import io.gihtub.katarem.application.port.input.GetBookingUseCase;
import io.gihtub.katarem.application.port.output.BookingOutputPort;
import io.gihtub.katarem.domain.model.Booking;
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
