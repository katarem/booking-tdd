package io.github.katarem.unit.application;

import io.github.katarem.application.port.input.ConfirmBookingUseCase;
import io.github.katarem.application.port.output.BookingOutputPort;
import io.github.katarem.application.usecase.ConfirmBookingUseCaseImpl;
import io.github.katarem.domain.model.Booking;
import io.github.katarem.domain.model.BookingStatus;
import io.github.katarem.domain.exception.impl.booking.BookingConfirmationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConfirmBookingUseCaseTest {

    @Mock
    BookingOutputPort outputPort;

    ConfirmBookingUseCase confirmBookingUseCase;

    @BeforeEach
    void setUp() {
        confirmBookingUseCase = new ConfirmBookingUseCaseImpl(outputPort);
    }

    @Test
    void confirm_booking_should_go_ok() {
        // given
        UUID bookingId = UUID.randomUUID();
        Booking booking = Booking.builder()
                .id(bookingId)
                .status(BookingStatus.PENDING)
                .build();

        // when
        when(outputPort.getBooking(bookingId))
                .thenReturn(booking);

        // then
        assertThatCode(() -> confirmBookingUseCase.confirmBooking(bookingId))
                .doesNotThrowAnyException();
    }

    @Test
    void confirm_booking_should_raise() {
        // given
        UUID bookingId = UUID.randomUUID();
        Booking booking = Booking.builder()
                .id(bookingId)
                .status(BookingStatus.CANCELLED)
                .build();

        // when
        when(outputPort.getBooking(bookingId))
                .thenReturn(booking);

        // then
        assertThatThrownBy(() -> confirmBookingUseCase.confirmBooking(bookingId))
                .isInstanceOf(BookingConfirmationException.class);

    }

}
