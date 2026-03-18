package io.gihtub.katarem.unit.application;

import io.gihtub.katarem.application.port.input.CreateBookingUseCase;
import io.gihtub.katarem.application.port.output.BookingOutputPort;
import io.gihtub.katarem.application.port.output.EmployeeQueryPort;
import io.gihtub.katarem.application.port.output.RoomQueryPort;
import io.gihtub.katarem.application.usecase.CreateBookingUseCaseImpl;
import io.gihtub.katarem.domain.model.Booking;
import io.gihtub.katarem.domain.model.Room;
import io.gihtub.katarem.domain.policy.ProfanityPolicy;
import io.gihtub.katarem.domain.policy.TolerancePolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.*;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateBookingUseCaseTest {

    CreateBookingUseCase createBookingUseCase;

    @Mock
    BookingOutputPort outputPort;

    @Mock
    EmployeeQueryPort employeeQueryPort;

    @Mock
    RoomQueryPort roomQueryPort;

    @Mock
    ProfanityPolicy profanityPolicy;

    @Mock
    TolerancePolicy tolerancePolicy;

    final Clock clock = Clock.fixed(
            Instant.parse("2026-03-18T00:00:00Z"),
            ZoneOffset.UTC
    );

    @BeforeEach
    void setUp() {
        createBookingUseCase = new CreateBookingUseCaseImpl(profanityPolicy, tolerancePolicy, outputPort, employeeQueryPort, roomQueryPort, clock);
    }

    @Test
    @DisplayName("Create Booking returns Booking")
    void create_booking_returns_booking(){

        // given
        LocalDateTime now = LocalDateTime.now().withHour(8);
        ZonedDateTime start = ZonedDateTime.of(now.plusHours(1), ZoneId.systemDefault());
        ZonedDateTime end = ZonedDateTime.of(now.plusHours(2), ZoneId.systemDefault());

        Booking booking = Booking.builder()
                .startDateTime(start)
                .endDateTime(end)
                .roomId(1)
                .employeeId(1)
                .attendeesCount(5)
                .build();

        // when
        when(outputPort.createBooking(booking))
                .thenReturn(Booking.builder().id(UUID.randomUUID()).build());

        when(employeeQueryPort.existsById(1))
                .thenReturn(true);

        when(roomQueryPort.getRoomById(1))
                .thenReturn(new Room(1, 10, true));

        // then
        Booking result = createBookingUseCase.createBooking(booking);

        assertThat(result.getId())
                .isNotNull()
                .isInstanceOf(UUID.class);

    }

    @Test
    @DisplayName("Create Booking throws database error")
    void create_booking_throws_database_error(){

        // given
        LocalDateTime now = LocalDateTime.now().withHour(8);
        ZonedDateTime start = ZonedDateTime.of(now.plusHours(1), ZoneId.systemDefault());
        ZonedDateTime end = ZonedDateTime.of(now.plusHours(2), ZoneId.systemDefault());
        Booking booking = Booking.builder()
                .startDateTime(start)
                .endDateTime(end)
                .build();

        // when
        when(employeeQueryPort.existsById(1))
                .thenReturn(true);

        when(roomQueryPort.getRoomById(1))
                .thenReturn(new Room(1, 10, true));

        when(outputPort.createBooking(booking))
                .thenThrow(RuntimeException.class);

        // then
        assertThatThrownBy(() -> createBookingUseCase.createBooking(booking))
                .isInstanceOf(RuntimeException.class);

    }

    @Test
    @DisplayName("Create Booking Employee does not exist")
    void create_booking_employee_does_not_exist(){

        // given
        LocalDateTime now = LocalDateTime.now().withHour(8);
        ZonedDateTime start = ZonedDateTime.of(now.plusHours(1), ZoneId.systemDefault());
        ZonedDateTime end = ZonedDateTime.of(now.plusHours(2), ZoneId.systemDefault());
        Booking booking = Booking.builder()
                .employeeId(1)
                .startDateTime(start)
                .endDateTime(end)
                .build();

        // when
        when(employeeQueryPort.existsById(1))
                .thenReturn(false);

        // then
        assertThatThrownBy(() -> createBookingUseCase.createBooking(booking))
                .isInstanceOf(RuntimeException.class);

    }

}