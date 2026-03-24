package io.github.katarem.domain.model;

import io.github.katarem.infraestructure.exception.impl.booking.BookingCancellationException;
import io.github.katarem.infraestructure.exception.impl.booking.BookingCancellationTimeExpiredException;
import io.github.katarem.infraestructure.exception.impl.booking.BookingConfirmationException;
import io.github.katarem.infraestructure.exception.impl.booking.BookingConflictException;
import io.github.katarem.infraestructure.exception.impl.booking.InvalidBookingDate;
import io.github.katarem.infraestructure.exception.impl.booking.InvalidBookingPeriod;
import io.github.katarem.infraestructure.exception.impl.booking.InvalidBookingStartDateException;
import io.github.katarem.infraestructure.exception.impl.room.InvalidCapacityForRoomException;
import io.github.katarem.infraestructure.exception.impl.room.RoomIsInactiveException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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
    @Builder.Default
    private BookingStatus status = BookingStatus.PENDING;
    private Integer attendeesCount;
    private ZonedDateTime createdAt;

    public void validateDates() {

        if(startDateTime.isAfter(endDateTime))
            throw new InvalidBookingStartDateException();

        if(ChronoUnit.MINUTES.between(startDateTime, endDateTime) < 30) {
            throw new InvalidBookingPeriod();
        }

        if(ChronoUnit.MINUTES.between(startDateTime, endDateTime) > 8 * 60) {
            throw new InvalidBookingPeriod();
        }

        validateDate(startDateTime);
        validateDate(endDateTime);

    }

    private void validateDate(ZonedDateTime date) {

        ZonedDateTime start = date.withHour(8).withMinute(0);
        ZonedDateTime end = date.withHour(20).withMinute(0);

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

    public void confirm() {
        if(this.status != BookingStatus.PENDING)
            throw new BookingConfirmationException();
        this.status = BookingStatus.CONFIRMED;
    }

    public void cancel(ZonedDateTime now) {
        if(this.status == BookingStatus.CANCELLED)
            throw new BookingCancellationException();
        if(ChronoUnit.MINUTES.between(now, startDateTime) < 15)
            throw new BookingCancellationTimeExpiredException();
        this.status = BookingStatus.CANCELLED;
    }

}
