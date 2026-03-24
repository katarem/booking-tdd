package io.gihtub.katarem.infraestructure.adapter.input.rest.request;

import io.gihtub.katarem.domain.model.BookingStatus;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ListBookingsRequest {
    private BookingStatus status;
    private Integer employeeId;
    private Integer roomId;
    private ZonedDateTime date;
}
