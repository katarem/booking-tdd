package io.gihtub.katarem.unit.application;

import io.gihtub.katarem.application.port.input.GetBookingUseCase;
import io.gihtub.katarem.application.usecase.GetBookingUseCaseImpl;
import io.gihtub.katarem.domain.model.Booking;
import io.gihtub.katarem.infraestructure.adapter.output.persistence.BookingPersistenceAdapter;
import io.gihtub.katarem.infraestructure.exception.BookingNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetBookingUseCaseTest {

    @Mock
    BookingPersistenceAdapter adapter;

    GetBookingUseCase getBookingUseCase;

    @BeforeEach
    void setUp() {
        getBookingUseCase = new GetBookingUseCaseImpl(adapter);
    }

    @Test
    @DisplayName("Get a booking returns booking")
    void test1() {

        // given
        UUID bookingId = UUID.randomUUID();
        Booking expected = Booking.builder()
                .id(bookingId)
                .build();

        // when
        when(adapter.getBooking(bookingId))
                .thenReturn(expected);

        // then
        Booking booking = getBookingUseCase.getBooking(bookingId);

        assertThat(booking.getId())
                .as("Booking ID should match requested ID")
                .isEqualTo(bookingId);

    }

    @Test
    @DisplayName("Get a booking does not found booking")
    void test2() {

        // given
        UUID bookingId = UUID.randomUUID();

        // when
        when(adapter.getBooking(bookingId))
                .thenThrow(new BookingNotFoundException(bookingId));

        // then
        assertThatThrownBy(() -> getBookingUseCase.getBooking(bookingId))
                .isInstanceOf(BookingNotFoundException.class)
                .hasMessage("Booking %s not found.", bookingId);


    }

}
