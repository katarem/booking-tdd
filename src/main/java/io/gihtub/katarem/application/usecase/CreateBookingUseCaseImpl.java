package io.gihtub.katarem.application.usecase;

import io.gihtub.katarem.application.port.input.CreateBookingUseCase;
import io.gihtub.katarem.application.port.output.BookingOutputPort;
import io.gihtub.katarem.domain.model.Booking;
import io.gihtub.katarem.domain.policy.ProfanityPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateBookingUseCaseImpl implements CreateBookingUseCase {

    private final BookingOutputPort outputPort;
    private final ProfanityPolicy profanityPolicy;

    @Override
    public Booking createBooking(Booking booking) {
        booking.validateDates();
        profanityPolicy.validateContent("title", booking.getTitle());
        profanityPolicy.validateContent("description", booking.getDescription());
        return outputPort.createBooking(booking);
    }
}
