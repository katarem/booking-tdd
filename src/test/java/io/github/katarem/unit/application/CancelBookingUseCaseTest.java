package io.github.katarem.unit.application;

import io.github.katarem.application.port.input.CancelBookingUseCase;
import io.github.katarem.application.port.output.BookingOutputPort;
import io.github.katarem.application.usecase.CancelBookingUseCaseImpl;
import io.github.katarem.domain.model.Booking;
import io.github.katarem.domain.model.BookingStatus;
import io.github.katarem.domain.exception.impl.booking.BookingCancellationException;
import io.github.katarem.domain.exception.impl.booking.BookingCancellationTimeExpiredException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CancelBookingUseCaseTest {

    @Mock
    BookingOutputPort bookingOutputPort;

    CancelBookingUseCase cancelBookingUseCase;

    private final Clock clock = Clock.fixed(
            Instant.parse("2026-03-18T00:00:00Z"),
            ZoneOffset.UTC
    );


    @BeforeEach
    void setUp() {
        cancelBookingUseCase = new CancelBookingUseCaseImpl(bookingOutputPort, clock);
    }

    @Test
    void cancel_booking_goes_ok(){
        // given
        UUID bookingId = UUID.randomUUID();
        ZonedDateTime now = ZonedDateTime.now(clock);
        Booking booking = Booking.builder()
                .id(bookingId)
                .startDateTime(now.plusMinutes(30))
                .status(BookingStatus.PENDING)
                .build();
        // when
        when(bookingOutputPort.getBooking(bookingId))
                .thenReturn(booking);
        // then
        assertThat(cancelBookingUseCase.cancelBooking(bookingId))
                .isNotNull();
    }

    @Test
    void cancel_booking_validation_time_error(){
        // given
        UUID bookingId = UUID.randomUUID();
        ZonedDateTime now = ZonedDateTime.now(clock);
        Booking booking = Booking.builder()
                .id(bookingId)
                .startDateTime(now.plusMinutes(10))
                .status(BookingStatus.CONFIRMED)
                .build();
        // when
        when(bookingOutputPort.getBooking(bookingId))
                .thenReturn(booking);
        // then
        assertThatThrownBy(() -> cancelBookingUseCase.cancelBooking(bookingId))
                .isInstanceOf(BookingCancellationTimeExpiredException.class);
    }

    @Test
    void cancel_booking_validation_error(){
        // given
        UUID bookingId = UUID.randomUUID();
        ZonedDateTime now = ZonedDateTime.now(clock);
        Booking booking = Booking.builder()
                .id(bookingId)
                .startDateTime(now.plusMinutes(20))
                .status(BookingStatus.CANCELLED)
                .build();
        // when
        when(bookingOutputPort.getBooking(bookingId))
                .thenReturn(booking);
        // then
        assertThatThrownBy(() -> cancelBookingUseCase.cancelBooking(bookingId))
                .isInstanceOf(BookingCancellationException.class);
    }

    @Test
    void cancel_booking_database_error(){
        // given
        UUID bookingId = UUID.randomUUID();

        // when
        when(bookingOutputPort.getBooking(bookingId))
                .thenThrow(RuntimeException.class);
        // then
        assertThatThrownBy(() -> cancelBookingUseCase.cancelBooking(bookingId))
                .isInstanceOf(RuntimeException.class);
    }
}
