package io.gihtub.katarem.infraestructure.adapter.input.rest.request;

import io.gihtub.katarem.domain.model.BookingStatus;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ListBookingsRequest {
    private BookingStatus status;
    private Integer employeeId;
    private Integer roomId;
    private ZonedDateTime date;
    @Min(1)
    private Integer page;
    @Min(10)
    private Integer size;
}
