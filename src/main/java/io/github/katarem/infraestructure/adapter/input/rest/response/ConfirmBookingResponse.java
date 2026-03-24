package io.github.katarem.infraestructure.adapter.input.rest.response;

import io.github.katarem.domain.model.BookingStatus;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
public class ConfirmBookingResponse {
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
}
