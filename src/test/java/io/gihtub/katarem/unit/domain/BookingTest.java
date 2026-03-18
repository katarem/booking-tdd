package io.gihtub.katarem.unit.domain;

import io.gihtub.katarem.domain.model.Booking;
import io.gihtub.katarem.domain.model.Room;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BookingTest {

    @Nested
    class RoomValidation {
        @Test
        @DisplayName("Booking has more attendees than room capacity")
        void booking_has_more_than_room_capacity() {

            // given
            Room room = Room.builder()
                    .active(true)
                    .capacity(5)
                    .build();

            Booking booking = Booking.builder()
                    .attendeesCount(10)
                    .build();

            // then
            assertThatThrownBy(() -> booking.validateRoom(room))
                    .isInstanceOf(RuntimeException.class);
        }

        @Test
        @DisplayName("Room is not active")
        void room_is_not_active() {

            // given
            Room room = Room.builder()
                    .active(true)
                    .capacity(5)
                    .build();

            Booking booking = Booking.builder()
                    .attendeesCount(10)
                    .build();

            // then
            assertThatThrownBy(() -> booking.validateRoom(room))
                    .isInstanceOf(RuntimeException.class);
        }

        @Test
        @DisplayName("Room should be valid with the same capacity and attendees")
        void room_ok_same_amount() {

            // given
            Room room = Room.builder()
                    .id(1)
                    .active(true)
                    .capacity(10)
                    .build();

            Booking booking = Booking.builder()
                    .attendeesCount(10)
                    .build();

            // then
            assertThatCode(() -> booking.validateRoom(room))
                    .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("Room should be valid with different amount of attendees and capcaity")
        void room_ok_different_amount() {

            // given
            Room room = Room.builder()
                    .id(1)
                    .active(true)
                    .capacity(10)
                    .build();

            Booking booking = Booking.builder()
                    .attendeesCount(5)
                    .build();

            // then
            assertThatCode(() -> booking.validateRoom(room))
                    .doesNotThrowAnyException();
        }
    }

    @Nested
    class DatesValidation {
        @Test
        @DisplayName("Create Booking invalid start date time is before it should")
        void booking_invalid_start_date_time_is_before_it_should(){

            // given
            LocalDateTime now = LocalDateTime.now().withHour(8);
            ZonedDateTime start = ZonedDateTime.of(now.withHour(5), ZoneId.systemDefault());
            ZonedDateTime end = ZonedDateTime.of(now.withHour(12), ZoneId.systemDefault());
            Booking booking = Booking.builder()
                    .startDateTime(start)
                    .endDateTime(end)
                    .build();

            // then
            assertThatThrownBy(booking::validateDates)
                    .isInstanceOf(RuntimeException.class);

        }

        @Test
        @DisplayName("Create Booking invalid start date time is after it should")
        void booking_invalid_start_date_time_is_after_it_should(){

            // given
            LocalDateTime now = LocalDateTime.now().withHour(8);
            ZonedDateTime start = ZonedDateTime.of(now.withHour(22), ZoneId.systemDefault());
            ZonedDateTime end = ZonedDateTime.of(now.withHour(23), ZoneId.systemDefault());
            Booking booking = Booking.builder()
                    .startDateTime(start)
                    .endDateTime(end)
                    .build();

            // then
            assertThatThrownBy(booking::validateDates)
                    .isInstanceOf(RuntimeException.class);

        }

        @Test
        @DisplayName("Create Booking invalid end date time is before it should")
        void booking_invalid_end_date_time_is_before_it_should(){

            // given
            LocalDateTime now = LocalDateTime.now().withHour(8);
            ZonedDateTime start = ZonedDateTime.of(now.withHour(8), ZoneId.systemDefault());
            ZonedDateTime end = ZonedDateTime.of(now.withHour(8), ZoneId.systemDefault());
            Booking booking = Booking.builder()
                    .startDateTime(start)
                    .endDateTime(end)
                    .build();

            // then
            assertThatThrownBy(booking::validateDates)
                    .isInstanceOf(RuntimeException.class);

        }

        @Test
        @DisplayName("Create Booking invalid start date time is after it should")
        void booking_invalid_end_date_time_is_after_it_should(){

            // given
            LocalDateTime now = LocalDateTime.now().withHour(8);
            ZonedDateTime start = ZonedDateTime.of(now.withHour(21), ZoneId.systemDefault());
            ZonedDateTime end = ZonedDateTime.of(now.withHour(23), ZoneId.systemDefault());
            Booking booking = Booking.builder()
                    .startDateTime(start)
                    .endDateTime(end)
                    .build();

            // then
            assertThatThrownBy(booking::validateDates)
                    .isInstanceOf(RuntimeException.class);

        }

        @Test
        @DisplayName("Create Booking invalid start date time is after it should")
        void booking_invalid_start_date_is_after_end_date(){

            // given
            LocalDateTime now = LocalDateTime.now().withHour(8);
            ZonedDateTime start = ZonedDateTime.of(now.withHour(17), ZoneId.systemDefault());
            ZonedDateTime end = ZonedDateTime.of(now.withHour(15), ZoneId.systemDefault());
            Booking booking = Booking.builder()
                    .startDateTime(start)
                    .endDateTime(end)
                    .build();

            // then
            assertThatThrownBy(booking::validateDates)
                    .isInstanceOf(RuntimeException.class);

        }

        @Test
        @DisplayName("Create Booking invalid dates difference is lower than 30 minutes")
        void booking_invalid_dates_difference_lower_than_30m(){

            // given
            LocalDateTime now = LocalDateTime.now().withHour(8);
            ZonedDateTime start = ZonedDateTime.of(now.withHour(17).withMinute(0), ZoneId.systemDefault());
            ZonedDateTime end = ZonedDateTime.of(now.withHour(17).withMinute(25), ZoneId.systemDefault());
            Booking booking = Booking.builder()
                    .startDateTime(start)
                    .endDateTime(end)
                    .build();

            // then
            assertThatThrownBy(booking::validateDates)
                    .isInstanceOf(RuntimeException.class);

        }

        @Test
        @DisplayName("Create Booking invalid dates difference is higher than 8 hours")
        void booking_invalid_dates_difference_higher_than_8h(){

            // given
            LocalDateTime now = LocalDateTime.now().withHour(8);
            ZonedDateTime start = ZonedDateTime.of(now.withHour(8).withMinute(0), ZoneId.systemDefault());
            ZonedDateTime end = ZonedDateTime.of(now.withHour(16).withMinute(30), ZoneId.systemDefault());
            Booking booking = Booking.builder()
                    .startDateTime(start)
                    .endDateTime(end)
                    .build();

            // then
            assertThatThrownBy(booking::validateDates)
                    .isInstanceOf(RuntimeException.class);

        }

        @Test
        @DisplayName("Create Booking with valid dates")
        void booking_valid_dates(){

            // given
            LocalDateTime now = LocalDateTime.now().withHour(8);
            ZonedDateTime start = ZonedDateTime.of(now.withHour(10).withMinute(0), ZoneId.systemDefault());
            ZonedDateTime end = ZonedDateTime.of(now.withHour(12).withMinute(30), ZoneId.systemDefault());
            Booking booking = Booking.builder()
                    .startDateTime(start)
                    .endDateTime(end)
                    .build();

            // then
            assertThatCode(booking::validateDates)
                    .doesNotThrowAnyException();

        }
    }


}
