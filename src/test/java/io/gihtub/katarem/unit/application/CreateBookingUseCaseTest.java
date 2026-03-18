package io.gihtub.katarem.unit.application;

import io.gihtub.katarem.application.port.input.CreateBookingUseCase;
import io.gihtub.katarem.application.port.output.BookingOutputPort;
import io.gihtub.katarem.application.usecase.CreateBookingUseCaseImpl;
import io.gihtub.katarem.domain.model.Booking;
import io.gihtub.katarem.domain.policy.ProfanityPolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
    ProfanityPolicy profanityPolicy;

    @BeforeEach
    void setUp() {
        createBookingUseCase = new CreateBookingUseCaseImpl(outputPort, profanityPolicy);
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
                .build();

        // when
        when(outputPort.createBooking(booking))
                .thenReturn(Booking.builder().id(UUID.randomUUID()).build());
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
        when(outputPort.createBooking(booking))
                .thenThrow(RuntimeException.class);
        // then
        assertThatThrownBy(() -> createBookingUseCase.createBooking(booking))
                .isInstanceOf(RuntimeException.class);

    }

    @Test
    @DisplayName("Create Booking invalid start date time is before it should")
    void create_booking_invalid_start_date_time_is_before_it_should(){

        // given
        LocalDateTime now = LocalDateTime.now().withHour(8);
        ZonedDateTime start = ZonedDateTime.of(now.withHour(5), ZoneId.systemDefault());
        ZonedDateTime end = ZonedDateTime.of(now.withHour(12), ZoneId.systemDefault());
        Booking booking = Booking.builder()
                .startDateTime(start)
                .endDateTime(end)
                .build();

        // then
        assertThatThrownBy(() -> createBookingUseCase.createBooking(booking))
                .isInstanceOf(RuntimeException.class);

    }

    @Test
    @DisplayName("Create Booking invalid start date time is after it should")
    void create_booking_invalid_start_date_time_is_after_it_should(){

        // given
        LocalDateTime now = LocalDateTime.now().withHour(8);
        ZonedDateTime start = ZonedDateTime.of(now.withHour(22), ZoneId.systemDefault());
        ZonedDateTime end = ZonedDateTime.of(now.withHour(23), ZoneId.systemDefault());
        Booking booking = Booking.builder()
                .startDateTime(start)
                .endDateTime(end)
                .build();

        // then
        assertThatThrownBy(() -> createBookingUseCase.createBooking(booking))
                .isInstanceOf(RuntimeException.class);

    }

    @Test
    @DisplayName("Create Booking invalid end date time is before it should")
    void create_booking_invalid_end_date_time_is_before_it_should(){

        // given
        LocalDateTime now = LocalDateTime.now().withHour(8);
        ZonedDateTime start = ZonedDateTime.of(now.withHour(8), ZoneId.systemDefault());
        ZonedDateTime end = ZonedDateTime.of(now.withHour(8), ZoneId.systemDefault());
        Booking booking = Booking.builder()
                .startDateTime(start)
                .endDateTime(end)
                .build();

        // then
        assertThatThrownBy(() -> createBookingUseCase.createBooking(booking))
                .isInstanceOf(RuntimeException.class);

    }

    @Test
    @DisplayName("Create Booking invalid start date time is after it should")
    void create_booking_invalid_end_date_time_is_after_it_should(){

        // given
        LocalDateTime now = LocalDateTime.now().withHour(8);
        ZonedDateTime start = ZonedDateTime.of(now.withHour(21), ZoneId.systemDefault());
        ZonedDateTime end = ZonedDateTime.of(now.withHour(23), ZoneId.systemDefault());
        Booking booking = Booking.builder()
                .startDateTime(start)
                .endDateTime(end)
                .build();

        // then
        assertThatThrownBy(() -> createBookingUseCase.createBooking(booking))
                .isInstanceOf(RuntimeException.class);

    }

    @Test
    @DisplayName("Create Booking invalid start date time is after it should")
    void create_booking_invalid_start_date_is_after_end_date(){

        // given
        LocalDateTime now = LocalDateTime.now().withHour(8);
        ZonedDateTime start = ZonedDateTime.of(now.withHour(17), ZoneId.systemDefault());
        ZonedDateTime end = ZonedDateTime.of(now.withHour(15), ZoneId.systemDefault());
        Booking booking = Booking.builder()
                .startDateTime(start)
                .endDateTime(end)
                .build();

        // then
        assertThatThrownBy(() -> createBookingUseCase.createBooking(booking))
                .isInstanceOf(RuntimeException.class);

    }

    @Test
    @DisplayName("Create Booking invalid dates difference is lower than 30 minutes")
    void create_booking_invalid_dates_difference_lower_than_30m(){

        // given
        LocalDateTime now = LocalDateTime.now().withHour(8);
        ZonedDateTime start = ZonedDateTime.of(now.withHour(17).withMinute(0), ZoneId.systemDefault());
        ZonedDateTime end = ZonedDateTime.of(now.withHour(17).withMinute(25), ZoneId.systemDefault());
        Booking booking = Booking.builder()
                .startDateTime(start)
                .endDateTime(end)
                .build();

        // then
        assertThatThrownBy(() -> createBookingUseCase.createBooking(booking))
                .isInstanceOf(RuntimeException.class);

    }

    @Test
    @DisplayName("Create Booking invalid dates difference is higher than 8 hours")
    void create_booking_invalid_dates_difference_higher_than_8h(){

        // given
        LocalDateTime now = LocalDateTime.now().withHour(8);
        ZonedDateTime start = ZonedDateTime.of(now.withHour(8).withMinute(0), ZoneId.systemDefault());
        ZonedDateTime end = ZonedDateTime.of(now.withHour(16).withMinute(30), ZoneId.systemDefault());
        Booking booking = Booking.builder()
                .startDateTime(start)
                .endDateTime(end)
                .build();

        // then
        assertThatThrownBy(() -> createBookingUseCase.createBooking(booking))
                .isInstanceOf(RuntimeException.class);

    }



    @Test
    @DisplayName("Create Booking has bad words in title")
    void create_booking_has_profanity_words_in_title() {

        // given
        LocalDateTime now = LocalDateTime.now().withHour(8);
        ZonedDateTime start = ZonedDateTime.of(now.plusHours(1), ZoneId.systemDefault());
        ZonedDateTime end = ZonedDateTime.of(now.plusHours(2), ZoneId.systemDefault());
        Booking booking = Booking.builder()
                .title("mierda de reunion")
                .startDateTime(start)
                .endDateTime(end)
                .build();

        // when
        doThrow(RuntimeException.class)
                .when(profanityPolicy).validateContent("title", booking.getTitle());

        // then
        assertThatThrownBy(() -> createBookingUseCase.createBooking(booking))
                .isInstanceOf(RuntimeException.class);

    }

    @Test
    @DisplayName("Create Booking has bad words in description")
    void create_booking_has_profanity_words_in_description() {

        // given
        LocalDateTime now = LocalDateTime.now().withHour(8);
        ZonedDateTime start = ZonedDateTime.of(now.plusHours(1), ZoneId.systemDefault());
        ZonedDateTime end = ZonedDateTime.of(now.plusHours(2), ZoneId.systemDefault());
        Booking booking = Booking.builder()
                .title("reunion")
                .description("reunion de gilipollas")
                .startDateTime(start)
                .endDateTime(end)
                .build();

        // when
        doThrow(RuntimeException.class)
                .when(profanityPolicy).validateContent("description", booking.getDescription());

        // then
        assertThatThrownBy(() -> createBookingUseCase.createBooking(booking))
                .isInstanceOf(RuntimeException.class);

    }

}