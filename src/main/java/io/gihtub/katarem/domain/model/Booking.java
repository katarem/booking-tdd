package io.gihtub.katarem.domain.model;

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

    public void validateDates() {

        if(startDateTime.isAfter(endDateTime))
            throw new RuntimeException();

        if(ChronoUnit.MINUTES.between(startDateTime, endDateTime) < 30) {
            throw new RuntimeException();
        }

        if(ChronoUnit.MINUTES.between(startDateTime, endDateTime) > 8 * 60) {
            throw new RuntimeException();
        }

        validateDate(startDateTime);
        validateDate(endDateTime);

    }

    private void validateDate(ZonedDateTime date) {
        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime start = ZonedDateTime.of(now.withHour(8).withMinute(0), ZoneId.systemDefault());
        ZonedDateTime end = ZonedDateTime.of(now.withHour(22).withMinute(0), ZoneId.systemDefault());

        if(date.isBefore(start) || date.isAfter(end)){
            throw new RuntimeException();
        }
    }

    public void validateRoom(Room room) {
        if(!room.isActive()) {
            throw new RuntimeException();
        }

        if(this.attendeesCount.compareTo(room.getCapacity()) > 0) {
            throw new RuntimeException();
        }
    }

}
