package io.gihtub.katarem.application.usecase;

import io.gihtub.katarem.application.port.input.CreateBookingUseCase;
import io.gihtub.katarem.application.port.output.BookingOutputPort;
import io.gihtub.katarem.application.port.output.EmployeeQueryPort;
import io.gihtub.katarem.application.port.output.RoomQueryPort;
import io.gihtub.katarem.domain.model.Booking;
import io.gihtub.katarem.domain.policy.ProfanityPolicy;
import io.gihtub.katarem.domain.policy.TolerancePolicy;
import io.gihtub.katarem.infraestructure.exception.impl.employee.EmployeeIsNotActiveException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class CreateBookingUseCaseImpl implements CreateBookingUseCase {

    private final ProfanityPolicy profanityPolicy;
    private final TolerancePolicy tolerancePolicy;

    private final BookingOutputPort outputPort;
    private final EmployeeQueryPort employeeQueryPort;
    private final RoomQueryPort roomQueryPort;

    private final Clock clock;

    @Override
    public Booking createBooking(Booking booking) {

        ZonedDateTime now = ZonedDateTime.now(clock);

        tolerancePolicy.validateBookingStartDateTolerance(booking.getStartDateTime(), now);

        booking.validateDates();

        profanityPolicy.validateContent("title", booking.getTitle());
        profanityPolicy.validateContent("description", booking.getDescription());

        if(!employeeQueryPort.existsById(booking.getEmployeeId())) {
            throw new EmployeeIsNotActiveException(booking.getEmployeeId());
        }

        var room = roomQueryPort.getRoomById(booking.getRoomId());
        booking.validateRoom(room);

        var bookingsForSameRoom = outputPort.getAllBookingsByRoomId(booking.getRoomId());
        bookingsForSameRoom.forEach(booking::validateDoesNotConflictWith);

        return outputPort.upsertBooking(booking);
    }
}
