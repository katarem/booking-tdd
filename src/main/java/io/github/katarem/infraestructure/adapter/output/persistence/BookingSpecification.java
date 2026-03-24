package io.github.katarem.infraestructure.adapter.output.persistence;

import io.github.katarem.domain.model.BookingStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.ZonedDateTime;

public class BookingSpecification {

    public static Specification<BookingEntity> dateMatches(ZonedDateTime date) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.equal(root.get("startDateTime"), date),
                criteriaBuilder.equal(root.get("endDateTime"), date)
        );
    }

    public static Specification<BookingEntity> employeeIdMatches(Integer employeeId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("employeeId"), employeeId);
    }

    public static Specification<BookingEntity> roomIdMatches(Integer roomId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("roomId"), roomId);
    }

    public static Specification<BookingEntity> statusMatches(BookingStatus bookingStatus) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), bookingStatus);
    }

}
