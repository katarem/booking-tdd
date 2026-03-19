package io.gihtub.katarem.unit.domain;

import io.gihtub.katarem.domain.model.Booking;
import io.gihtub.katarem.domain.model.BookingStatus;
import io.gihtub.katarem.domain.model.Room;
import io.gihtub.katarem.infraestructure.exception.impl.booking.*;
import io.gihtub.katarem.infraestructure.exception.impl.room.InvalidCapacityForRoomException;
import io.gihtub.katarem.infraestructure.exception.impl.room.RoomIsInactiveException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.*;

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
                    .isInstanceOf(InvalidCapacityForRoomException.class);
        }

        @Test
        @DisplayName("Room is not active")
        void room_is_not_active() {

            // given
            Room room = Room.builder()
                    .active(false)
                    .capacity(10)
                    .build();

            Booking booking = Booking.builder()
                    .attendeesCount(10)
                    .build();

            // then
            assertThatThrownBy(() -> booking.validateRoom(room))
                    .isInstanceOf(RoomIsInactiveException.class);
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

        private final Clock clock = Clock.fixed(
                Instant.parse("2026-03-18T00:00:00Z"),
                ZoneOffset.UTC
        );

        @Test
        @DisplayName("Create Booking invalid start date time is before it should")
        void booking_invalid_start_date_time_is_before_it_should(){

            // given
            ZonedDateTime now = ZonedDateTime.now(clock).withHour(5);
            ZonedDateTime start = now.withHour(5);
            ZonedDateTime end = now.withHour(12);
            Booking booking = Booking.builder()
                    .startDateTime(start)
                    .endDateTime(end)
                    .build();

            // then
            assertThatThrownBy(() -> booking.validateDates(now))
                    .isInstanceOf(InvalidBookingDate.class);

        }

        @Test
        @DisplayName("Create Booking invalid start date time is after it should")
        void booking_invalid_start_date_time_is_after_it_should(){

            // given
            ZonedDateTime now = ZonedDateTime.now(clock).withHour(8);
            ZonedDateTime start = now.withHour(22);
            ZonedDateTime end = now.withHour(23);
            Booking booking = Booking.builder()
                    .startDateTime(start)
                    .endDateTime(end)
                    .build();

            // then
            assertThatThrownBy(() -> booking.validateDates(now))
                    .isInstanceOf(InvalidBookingDate.class);

        }

        @Test
        @DisplayName("Create Booking invalid end date time is before it should")
        void booking_invalid_end_date_time_is_before_it_should(){

            // given
            ZonedDateTime now = ZonedDateTime.now(clock).withHour(8);
            ZonedDateTime start = now.withHour(8);
            ZonedDateTime end = now.withHour(8);
            Booking booking = Booking.builder()
                    .startDateTime(start)
                    .endDateTime(end)
                    .build();

            // then
            assertThatThrownBy(() -> booking.validateDates(now))
                    .isInstanceOf(InvalidBookingPeriod.class);

        }

        @Test
        @DisplayName("Create Booking invalid start date time is after it should")
        void booking_invalid_end_date_time_is_after_it_should(){

            // given
            ZonedDateTime now = ZonedDateTime.now(clock).withHour(8);
            ZonedDateTime start = now.withHour(19);
            ZonedDateTime end = now.withHour(23);
            Booking booking = Booking.builder()
                    .startDateTime(start)
                    .endDateTime(end)
                    .build();

            // then
            assertThatThrownBy(() -> booking.validateDates(now))
                    .isInstanceOf(InvalidBookingDate.class);

        }

        @Test
        @DisplayName("Create Booking invalid start date time is after it should")
        void booking_invalid_start_date_is_after_end_date(){

            // given
            ZonedDateTime now = ZonedDateTime.now(clock).withHour(8);
            ZonedDateTime start = now.withHour(17);
            ZonedDateTime end = now.withHour(15);
            Booking booking = Booking.builder()
                    .startDateTime(start)
                    .endDateTime(end)
                    .build();

            // then
            assertThatThrownBy(() -> booking.validateDates(now))
                    .isInstanceOf(InvalidBookingStartDateException.class);

        }

        @Test
        @DisplayName("Create Booking invalid dates difference is lower than 30 minutes")
        void booking_invalid_dates_difference_lower_than_30m(){

            // given
            ZonedDateTime now = ZonedDateTime.now(clock).withHour(8);
            ZonedDateTime start = now.withHour(17).withMinute(0);
            ZonedDateTime end = now.withHour(17).withMinute(25);
            Booking booking = Booking.builder()
                    .startDateTime(start)
                    .endDateTime(end)
                    .build();

            // then
            assertThatThrownBy(() -> booking.validateDates(now))
                    .isInstanceOf(InvalidBookingPeriod.class);

        }

        @Test
        @DisplayName("Create Booking invalid dates difference is higher than 8 hours")
        void booking_invalid_dates_difference_higher_than_8h(){

            // given
            ZonedDateTime now = ZonedDateTime.now(clock).withHour(8);
            ZonedDateTime start = now.withHour(8).withMinute(0);
            ZonedDateTime end = now.withHour(16).withMinute(30);
            Booking booking = Booking.builder()
                    .startDateTime(start)
                    .endDateTime(end)
                    .build();

            // then
            assertThatThrownBy(() -> booking.validateDates(now))
                    .isInstanceOf(InvalidBookingPeriod.class);

        }

        @Test
        @DisplayName("Create Booking with valid dates")
        void booking_valid_dates(){

            // given
            ZonedDateTime now = ZonedDateTime.now(clock).withHour(8);
            ZonedDateTime start = now.withHour(10).withMinute(0);
            ZonedDateTime end = now.withHour(12).withMinute(30);
            Booking booking = Booking.builder()
                    .startDateTime(start)
                    .endDateTime(end)
                    .build();

            // then
            assertThatCode(() -> booking.validateDates(now))
                    .doesNotThrowAnyException();

        }
    }

    @Nested
    class BookingConflictValidation {

        private final Clock clock = Clock.fixed(
                Instant.parse("2026-03-18T00:00:00Z"),
                ZoneOffset.UTC
        );

        @Test
        @DisplayName("Booking does not overlap because starts before other ends should not throw")
        void booking_does_not_overlap_should_go_ok() {

            //given
            ZonedDateTime now = ZonedDateTime.now(clock);

            ZonedDateTime originalStart = now.withHour(8);
            ZonedDateTime originalEnd = now.withHour(10);

            Booking original = Booking.builder()
                    .startDateTime(originalStart)
                    .endDateTime(originalEnd)
                    .build();

            ZonedDateTime newStart = now.withHour(10);
            ZonedDateTime newEnd =now.withHour(11);

            Booking newBooking = Booking.builder()
                    .startDateTime(newStart)
                    .endDateTime(newEnd)
                    .build();

            //then
            assertThatCode(() -> original.validateDoesNotConflictWith(newBooking))
                    .doesNotThrowAnyException();

        }

        @Test
        @DisplayName("Booking does not overlap because starts after other ends should not throw")
        void booking_does_not_overlap_because_starts_after_other_ends_should_go_ok() {

            ZonedDateTime now = ZonedDateTime.now(clock);

            ZonedDateTime originalStart = now.withHour(12).withMinute(0);
            ZonedDateTime originalEnd = now.withHour(14).withMinute(0);

            Booking original = Booking.builder()
                    .startDateTime(originalStart)
                    .endDateTime(originalEnd)
                    .build();

            ZonedDateTime newStart = now.withHour(9).withMinute(0);
            ZonedDateTime newEnd = now.withHour(11).withMinute(0);

            Booking newBooking = Booking.builder()
                    .startDateTime(newStart)
                    .endDateTime(newEnd)
                    .build();

            assertThatCode(() -> original.validateDoesNotConflictWith(newBooking))
                    .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("Booking does overlap start date after should throw exception")
        void booking_does_overlap_start_date_is_after_should_throw() {

            //given
            ZonedDateTime now = ZonedDateTime.now(clock);

            ZonedDateTime originalStart = now.withHour(8);
            ZonedDateTime originalEnd = now.withHour(10);

            Booking original = Booking.builder()
                    .startDateTime(originalStart)
                    .endDateTime(originalEnd)
                    .build();

            ZonedDateTime newStart = now.withHour(9);
            ZonedDateTime newEnd = now.withHour(9).withMinute(30);

            Booking newBooking = Booking.builder()
                    .startDateTime(newStart)
                    .endDateTime(newEnd)
                    .build();

            //then
            assertThatThrownBy(() -> original.validateDoesNotConflictWith(newBooking))
                    .isInstanceOf(BookingConflictException.class);

        }

        @Test
        @DisplayName("Booking does overlap end date is before should throw exception")
        void booking_does_overlap_end_date_is_before_should_throw() {

            //given
            ZonedDateTime now = ZonedDateTime.now(clock);

            ZonedDateTime originalStart = now.withHour(10);
            ZonedDateTime originalEnd = now.withHour(12);

            Booking original = Booking.builder()
                    .startDateTime(originalStart)
                    .endDateTime(originalEnd)
                    .build();

            ZonedDateTime newStart = now.withHour(9);
            ZonedDateTime newEnd = now.withHour(11).withMinute(30);

            Booking newBooking = Booking.builder()
                    .startDateTime(newStart)
                    .endDateTime(newEnd)
                    .build();

            //then
            assertThatThrownBy(() -> original.validateDoesNotConflictWith(newBooking))
                    .isInstanceOf(BookingConflictException.class);

        }


    }

    @Nested
    class ConfirmFeature {

        @Test
        void confirm_booking_should_be_ok() {
            // given
            Booking booking = Booking.builder()
                    .status(BookingStatus.PENDING)
                    .build();
            // then
            assertThatCode(booking::confirm)
                    .doesNotThrowAnyException();
        }

        @Test
        void confirm_booking_should_fail_on_cancelled() {
            // given
            Booking booking = Booking.builder()
                    .status(BookingStatus.CANCELLED)
                    .build();
            // then
            assertThatThrownBy(booking::confirm)
                    .isInstanceOf(BookingConfirmationException.class);

        }

        @Test
        void confirm_booking_should_fail_on_confirmed() {
            // given
            Booking booking = Booking.builder()
                    .status(BookingStatus.CONFIRMED)
                    .build();
            // then
            assertThatThrownBy(booking::confirm)
                    .isInstanceOf(BookingConfirmationException.class);

        }

    }

}
