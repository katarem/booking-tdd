package io.gihtub.katarem.domain.criteria;

import io.gihtub.katarem.domain.model.BookingStatus;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class BookingCriteria {
    private BookingStatus status;
    private Integer employeeId;
    private Integer roomId;
    private ZonedDateTime date;
}
