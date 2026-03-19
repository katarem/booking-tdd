package io.gihtub.katarem.infraestructure.adapter.output.persistence;

import io.gihtub.katarem.domain.model.BookingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity(name = "booking")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Integer employeeId;
    private Integer roomId;
    private String title;
    private String description;
    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;
    @EnumeratedValue
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
    private Integer attendeesCount;
    private ZonedDateTime createdAt;
}
