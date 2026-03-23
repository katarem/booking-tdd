package io.gihtub.katarem.infraestructure.adapter.input.rest.request;

import io.gihtub.katarem.domain.model.BookingStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
    private UUID id;
    @NotNull
    @Min(1)
    private Integer employeeId;
    @NotNull
    @Min(1)
    private Integer roomId;
    @NotNull
    @Size(min = 3, max = 100)
    private String title;
    @NotNull
    @Size(max = 500)
    private String description;
    @NotNull
    private ZonedDateTime startDateTime;
    @NotNull
    private ZonedDateTime endDateTime;
    @NotNull
    private BookingStatus status;
    @NotNull
    @Min(1)
    private Integer attendeesCount;
    private ZonedDateTime createdAt;
}
