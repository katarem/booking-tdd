package io.gihtub.katarem.application.usecase;

import io.gihtub.katarem.application.port.input.ListBookingUseCase;
import io.gihtub.katarem.application.port.output.BookingOutputPort;
import io.gihtub.katarem.domain.criteria.BookingCriteria;
import io.gihtub.katarem.domain.criteria.PageCriteria;
import io.gihtub.katarem.domain.model.Booking;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ListBookingUseCaseImpl implements ListBookingUseCase {

    private final BookingOutputPort outputPort;

    @Override
    public Set<Booking> listBookings(BookingCriteria bookingCriteria, PageCriteria pageCriteria) {
        return outputPort.getBookings(bookingCriteria, pageCriteria);
    }
}
