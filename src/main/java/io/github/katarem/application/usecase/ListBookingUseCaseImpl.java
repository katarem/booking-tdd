package io.github.katarem.application.usecase;

import io.github.katarem.application.port.input.ListBookingUseCase;
import io.github.katarem.application.port.output.BookingOutputPort;
import io.github.katarem.domain.criteria.BookingCriteria;
import io.github.katarem.domain.criteria.PageCriteria;
import io.github.katarem.domain.model.BookingList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListBookingUseCaseImpl implements ListBookingUseCase {

    private final BookingOutputPort outputPort;

    @Override
    public BookingList listBookings(BookingCriteria bookingCriteria, PageCriteria pageCriteria) {
        return outputPort.getBookings(bookingCriteria, pageCriteria);
    }
}
