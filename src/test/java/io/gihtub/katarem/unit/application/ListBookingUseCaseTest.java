package io.gihtub.katarem.unit.application;

import io.gihtub.katarem.application.port.input.ListBookingUseCase;
import io.gihtub.katarem.application.port.output.BookingOutputPort;
import io.gihtub.katarem.application.usecase.ListBookingUseCaseImpl;
import io.gihtub.katarem.domain.criteria.PageCriteria;
import io.gihtub.katarem.domain.model.Booking;
import io.gihtub.katarem.domain.model.BookingList;
import io.gihtub.katarem.domain.model.BookingStatus;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class ListBookingUseCaseTest {

    @Mock
    BookingOutputPort bookingOutputPort;

    ListBookingUseCase listBookingUseCase;

    final Set<Booking> BOOKINGS = Set.of(
            Booking.builder()
                    .id(UUID.randomUUID())
                    .status(BookingStatus.PENDING)
                    .startDateTime(ZonedDateTime.parse("2026-03-20T09:00:00+01:00[Europe/Madrid]"))
                    .endDateTime(ZonedDateTime.parse("2026-03-20T10:00:00+01:00[Europe/Madrid]"))
                    .employeeId(1)
                    .roomId(101)
                    .build(),

            Booking.builder()
                    .id(UUID.randomUUID())
                    .status(BookingStatus.CONFIRMED)
                    .startDateTime(ZonedDateTime.parse("2026-03-20T10:00:00+01:00[Europe/Madrid]"))
                    .endDateTime(ZonedDateTime.parse("2026-03-20T11:00:00+01:00[Europe/Madrid]"))
                    .employeeId(2)
                    .roomId(101)
                    .build(),

            Booking.builder()
                    .id(UUID.randomUUID())
                    .status(BookingStatus.CANCELLED)
                    .startDateTime(ZonedDateTime.parse("2026-03-20T11:00:00+01:00[Europe/Madrid]"))
                    .endDateTime(ZonedDateTime.parse("2026-03-20T12:00:00+01:00[Europe/Madrid]"))
                    .employeeId(1)
                    .roomId(102)
                    .build(),

            Booking.builder()
                    .id(UUID.randomUUID())
                    .status(BookingStatus.CONFIRMED)
                    .startDateTime(ZonedDateTime.parse("2026-03-21T09:00:00+01:00[Europe/Madrid]"))
                    .endDateTime(ZonedDateTime.parse("2026-03-21T10:30:00+01:00[Europe/Madrid]"))
                    .employeeId(3)
                    .roomId(101)
                    .build(),

            Booking.builder()
                    .id(UUID.randomUUID())
                    .status(BookingStatus.PENDING)
                    .startDateTime(ZonedDateTime.parse("2026-03-21T10:30:00+01:00[Europe/Madrid]"))
                    .endDateTime(ZonedDateTime.parse("2026-03-21T11:30:00+01:00[Europe/Madrid]"))
                    .employeeId(2)
                    .roomId(103)
                    .build(),

            Booking.builder()
                    .id(UUID.randomUUID())
                    .status(BookingStatus.CANCELLED)
                    .startDateTime(ZonedDateTime.parse("2026-03-21T12:00:00+01:00[Europe/Madrid]"))
                    .endDateTime(ZonedDateTime.parse("2026-03-21T13:00:00+01:00[Europe/Madrid]"))
                    .employeeId(1)
                    .roomId(103)
                    .build(),

            Booking.builder()
                    .id(UUID.randomUUID())
                    .status(BookingStatus.CONFIRMED)
                    .startDateTime(ZonedDateTime.parse("2026-03-22T08:00:00+01:00[Europe/Madrid]"))
                    .endDateTime(ZonedDateTime.parse("2026-03-22T09:00:00+01:00[Europe/Madrid]"))
                    .employeeId(3)
                    .roomId(102)
                    .build(),

            Booking.builder()
                    .id(UUID.randomUUID())
                    .status(BookingStatus.PENDING)
                    .startDateTime(ZonedDateTime.parse("2026-03-22T09:00:00+01:00[Europe/Madrid]"))
                    .endDateTime(ZonedDateTime.parse("2026-03-22T10:00:00+01:00[Europe/Madrid]"))
                    .employeeId(1)
                    .roomId(101)
                    .build()
    );

    @BeforeEach
    void setUp() {
        listBookingUseCase = new ListBookingUseCaseImpl(bookingOutputPort);
    }

    @Test
    void should_return_bookings_from_output_port() {

        // given
        PageCriteria pageCriteria = new PageCriteria();
        pageCriteria.setSize(100);
        pageCriteria.setPage(1);

        // when
        when(bookingOutputPort.getBookings(null, pageCriteria)).thenReturn(
                BookingList.builder()
                        .bookings(BOOKINGS)
                        .page(1)
                        .totalPages(1)
                        .size(100)
                        .build()
        );

        // then
        assertThat(listBookingUseCase.listBookings(null, pageCriteria))
                .isNotNull()
                .hasFieldOrPropertyWithValue("bookings", BOOKINGS);
    }

    @Test
    void should_return_empty_set_when_output_port_returns_empty_set() {

        // given
        PageCriteria pageCriteria = new PageCriteria();
        pageCriteria.setSize(100);
        pageCriteria.setPage(1);

        // when
        when(bookingOutputPort.getBookings(null, pageCriteria)).thenReturn(
                BookingList.builder()
                        .bookings(Collections.emptySet())
                        .page(1)
                        .totalPages(1)
                        .size(100)
                        .build()
        );

        // then
        assertThat(listBookingUseCase.listBookings(null, pageCriteria))
                .isNotNull()
                .hasFieldOrPropertyWithValue("bookings", Collections.emptySet());

    }

    @Test
    void should_throw_when_paging_is_not_given() {

        // when
        when(bookingOutputPort.getBookings(null, null))
                .thenThrow(RuntimeException.class);

        // then
        assertThatThrownBy(() -> listBookingUseCase.listBookings(null, null))
                .isInstanceOf(RuntimeException.class);

    }

}
