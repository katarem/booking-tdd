package io.gihtub.katarem.domain.model;

import io.gihtub.katarem.infraestructure.exception.impl.booking.BookingConflictException;
import io.gihtub.katarem.infraestructure.exception.impl.booking.InvalidBookingDate;
import io.gihtub.katarem.infraestructure.exception.impl.booking.InvalidBookingPeriod;
import io.gihtub.katarem.infraestructure.exception.impl.booking.InvalidBookingStartDateException;
import io.gihtub.katarem.infraestructure.exception.impl.room.InvalidCapacityForRoomException;
import io.gihtub.katarem.infraestructure.exception.impl.room.RoomIsInactiveException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Getter
public class Booking {

    private UUID id;
    private Integer employeeId;
    private Integer roomId;
    private String title;
    private String description;
    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;
    private BookingStatus status;
    private Integer attendeesCount;
    private ZonedDateTime createdAt;

    public void validateDates(LocalDateTime now) {
        if(startDateTime.isAfter(endDateTime))
            throw new InvalidBookingStartDateException();

        if(ChronoUnit.MINUTES.between(startDateTime, endDateTime) < 30) {
            throw new InvalidBookingPeriod();
        }

        if(ChronoUnit.MINUTES.between(startDateTime, endDateTime) > 8 * 60) {
            throw new InvalidBookingPeriod();
        }

        if(ChronoUnit.MINUTES.between(startDateTime, now) > 15) {
            throw new InvalidBookingStartDateException();
        }

        validateDate(startDateTime, now);
        validateDate(endDateTime, now);

    }

    private void validateDate(ZonedDateTime date, LocalDateTime now) {

        ZonedDateTime start = ZonedDateTime.of(now.withHour(8).withMinute(0), ZoneId.systemDefault());
        ZonedDateTime end = ZonedDateTime.of(now.withHour(20).withMinute(0), ZoneId.systemDefault());

        if(date.isBefore(start) || date.isAfter(end)){
            throw new InvalidBookingDate();
        }
    }

    public void validateRoom(Room room) {
        if(!room.isActive()) {
            throw new RoomIsInactiveException(roomId);
        }

        if(this.attendeesCount.compareTo(room.getCapacity()) > 0) {
            throw new InvalidCapacityForRoomException(roomId);
        }
    }

    public void validateDoesNotConflictWith(Booking other) {
        if(this.startDateTime.isBefore(other.endDateTime)
            && this.endDateTime.isAfter(other.startDateTime))
            throw new BookingConflictException();
    }

}
