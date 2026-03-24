package io.github.katarem.domain.criteria;

import io.github.katarem.domain.model.BookingStatus;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class BookingCriteria {
    private BookingStatus status;
    private Integer employeeId;
    private Integer roomId;
    private ZonedDateTime date;
}
