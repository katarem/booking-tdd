package io.gihtub.katarem.application.usecase;

import io.gihtub.katarem.application.port.input.CreateBookingUseCase;
import io.gihtub.katarem.application.port.output.BookingOutputPort;
import io.gihtub.katarem.application.port.output.EmployeeQueryPort;
import io.gihtub.katarem.application.port.output.RoomQueryPort;
import io.gihtub.katarem.domain.model.Booking;
import io.gihtub.katarem.domain.policy.ProfanityPolicy;
import io.gihtub.katarem.infraestructure.exception.impl.employee.EmployeeIsNotActiveException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreateBookingUseCaseImpl implements CreateBookingUseCase {

    private final BookingOutputPort outputPort;
    private final ProfanityPolicy profanityPolicy;
    private final EmployeeQueryPort employeeQueryPort;
    private final RoomQueryPort roomQueryPort;
    private final Clock clock;

    @Override
    public Booking createBooking(Booking booking) {

        LocalDateTime now = LocalDateTime.now(clock);
        booking.validateDates(now);

        profanityPolicy.validateContent("title", booking.getTitle());
        profanityPolicy.validateContent("description", booking.getDescription());

        if(!employeeQueryPort.existsById(booking.getEmployeeId())) {
            throw new EmployeeIsNotActiveException(booking.getEmployeeId());
        }

        var room = roomQueryPort.getRoomById(booking.getRoomId());
        booking.validateRoom(room);

        return outputPort.createBooking(booking);
    }
}
