package io.gihtub.katarem.data.infrastructure;

import io.gihtub.katarem.domain.model.BookingStatus;
import io.gihtub.katarem.infraestructure.adapter.output.persistence.BookingEntity;
import io.gihtub.katarem.infraestructure.adapter.output.persistence.BookingJpaOutput;
import io.gihtub.katarem.infraestructure.adapter.output.persistence.BookingSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BookingJpaOutputTest {

    @Autowired
    BookingJpaOutput jpaOutput;

    List<BookingEntity> bookings;

    @AfterEach
    void tearDown() {
        jpaOutput.deleteAll();
    }

    @Test
    void list_bookings_without_filters() {
        bookings = jpaOutput.saveAll(
                List.of(
                        // Room 1
                        BookingEntity.builder().roomId(1).title("Reunión mañanera").startDateTime(ZonedDateTime.parse("2026-02-26T10:00:00Z", DateTimeFormatter.ISO_DATE_TIME)).build(),
                        BookingEntity.builder().roomId(1).title("Reunión mañanera").startDateTime(ZonedDateTime.parse("2026-02-26T10:00:00Z", DateTimeFormatter.ISO_DATE_TIME)).build(),
                        BookingEntity.builder().roomId(1).title("Reunión mañanera").startDateTime(ZonedDateTime.parse("2026-02-26T10:00:00Z", DateTimeFormatter.ISO_DATE_TIME)).build(),
                        // Other room
                        BookingEntity.builder().roomId(2).title("Reunión mañanera").startDateTime(ZonedDateTime.parse("2026-02-26T10:00:00Z", DateTimeFormatter.ISO_DATE_TIME)).build(),
                        BookingEntity.builder().roomId(2).title("Reunión mañanera").startDateTime(ZonedDateTime.parse("2026-02-26T10:00:00Z", DateTimeFormatter.ISO_DATE_TIME)).build(),
                        BookingEntity.builder().roomId(2).title("Reunión mañanera").startDateTime(ZonedDateTime.parse("2026-02-26T10:00:00Z", DateTimeFormatter.ISO_DATE_TIME)).build()
                )
        );

        assertThat(jpaOutput.findAll(Specification.unrestricted(), PageRequest.of(0, 10)))
                .hasSameSizeAs(bookings);
    }

    @Test
    void list_bookings_with_room_id_filter() {
        bookings = jpaOutput.saveAll(
                List.of(
                        // Room 1
                        BookingEntity.builder().roomId(1).title("Reunión mañanera").startDateTime(ZonedDateTime.parse("2026-02-26T10:00:00Z", DateTimeFormatter.ISO_DATE_TIME)).build(),
                        BookingEntity.builder().roomId(1).title("Reunión mañanera").startDateTime(ZonedDateTime.parse("2026-02-26T10:00:00Z", DateTimeFormatter.ISO_DATE_TIME)).build(),
                        BookingEntity.builder().roomId(1).title("Reunión mañanera").startDateTime(ZonedDateTime.parse("2026-02-26T10:00:00Z", DateTimeFormatter.ISO_DATE_TIME)).build(),
                        // Other room
                        BookingEntity.builder().roomId(2).title("Reunión mañanera").startDateTime(ZonedDateTime.parse("2026-02-26T10:00:00Z", DateTimeFormatter.ISO_DATE_TIME)).build(),
                        BookingEntity.builder().roomId(2).title("Reunión mañanera").startDateTime(ZonedDateTime.parse("2026-02-26T10:00:00Z", DateTimeFormatter.ISO_DATE_TIME)).build(),
                        BookingEntity.builder().roomId(2).title("Reunión mañanera").startDateTime(ZonedDateTime.parse("2026-02-26T10:00:00Z", DateTimeFormatter.ISO_DATE_TIME)).build()
                )
        );

        assertThat(jpaOutput.findAll(BookingSpecification.roomIdMatches(1), PageRequest.ofSize(10)))
                .hasSize(3);

    }

    @Test
    void list_bookings_with_date_filter() {
        bookings = jpaOutput.saveAll(
                List.of(
                        // Room 1
                        BookingEntity.builder().roomId(1).title("Reunión mañanera").startDateTime(ZonedDateTime.parse("2026-02-10T10:00:00Z", DateTimeFormatter.ISO_DATE_TIME))
                                .endDateTime(ZonedDateTime.parse("2026-02-28T10:00:00Z", DateTimeFormatter.ISO_DATE_TIME)).build(),
                        BookingEntity.builder().roomId(1).title("Reunión mañanera").startDateTime(ZonedDateTime.parse("2026-02-26T10:00:00Z", DateTimeFormatter.ISO_DATE_TIME))
                                .endDateTime(ZonedDateTime.parse("2026-03-01T10:00:00Z", DateTimeFormatter.ISO_DATE_TIME)).build(),
                        BookingEntity.builder().roomId(1).title("Reunión mañanera").startDateTime(ZonedDateTime.parse("2026-02-21T10:00:00Z", DateTimeFormatter.ISO_DATE_TIME))
                                .endDateTime(ZonedDateTime.parse("2026-03-01T10:00:00Z", DateTimeFormatter.ISO_DATE_TIME)).build(),
                        // Other room
                        BookingEntity.builder().roomId(2).title("Reunión mañanera").startDateTime(ZonedDateTime.parse("2026-02-10T10:00:00Z", DateTimeFormatter.ISO_DATE_TIME))
                                .endDateTime(ZonedDateTime.parse("2026-02-23T10:00:00Z", DateTimeFormatter.ISO_DATE_TIME)).build(),
                        BookingEntity.builder().roomId(2).title("Reunión mañanera").startDateTime(ZonedDateTime.parse("2026-02-15T10:00:00Z", DateTimeFormatter.ISO_DATE_TIME))
                                .endDateTime(ZonedDateTime.parse("2026-02-26T10:00:00Z", DateTimeFormatter.ISO_DATE_TIME)).build(),
                        BookingEntity.builder().roomId(2).title("Reunión mañanera").startDateTime(ZonedDateTime.parse("2026-02-22T10:00:00Z", DateTimeFormatter.ISO_DATE_TIME))
                                .endDateTime(ZonedDateTime.parse("2026-02-22T10:00:00Z", DateTimeFormatter.ISO_DATE_TIME)).build()
                )
        );

        var date = ZonedDateTime.parse("2026-02-26T10:00:00Z", DateTimeFormatter.ISO_DATE_TIME);

        assertThat(jpaOutput.findAll(BookingSpecification.dateMatches(date), PageRequest.ofSize(10)))
                .hasSize(2);

    }

    @Test
    void list_bookings_with_employee_id_filter() {
        bookings = jpaOutput.saveAll(
                List.of(
                        // Room 1
                        BookingEntity.builder().roomId(1).title("Reunión mañanera").employeeId(1).startDateTime(ZonedDateTime.parse("2026-02-26T10:00:00Z", DateTimeFormatter.ISO_DATE_TIME)).build(),
                        BookingEntity.builder().roomId(1).title("Reunión mañanera").employeeId(2).startDateTime(ZonedDateTime.parse("2026-02-26T10:00:00Z", DateTimeFormatter.ISO_DATE_TIME)).build(),
                        BookingEntity.builder().roomId(1).title("Reunión mañanera").employeeId(1).startDateTime(ZonedDateTime.parse("2026-02-26T10:00:00Z", DateTimeFormatter.ISO_DATE_TIME)).build(),
                        // Other room
                        BookingEntity.builder().roomId(2).title("Reunión mañanera").employeeId(1).startDateTime(ZonedDateTime.parse("2026-02-26T10:00:00Z", DateTimeFormatter.ISO_DATE_TIME)).build(),
                        BookingEntity.builder().roomId(2).title("Reunión mañanera").employeeId(2).startDateTime(ZonedDateTime.parse("2026-02-26T10:00:00Z", DateTimeFormatter.ISO_DATE_TIME)).build(),
                        BookingEntity.builder().roomId(2).title("Reunión mañanera").employeeId(2).startDateTime(ZonedDateTime.parse("2026-02-26T10:00:00Z", DateTimeFormatter.ISO_DATE_TIME)).build()
                )
        );

        assertThat(jpaOutput.findAll(BookingSpecification.roomIdMatches(1), PageRequest.ofSize(10)))
                .hasSize(3);

    }

    @Test
    void list_bookings_multiple_filter() {
        bookings = jpaOutput.saveAll(
                List.of(
                        // Room 1
                        BookingEntity.builder().roomId(1).title("Reunión mañanera").employeeId(1).startDateTime(ZonedDateTime.parse("2026-02-26T10:00:00Z", DateTimeFormatter.ISO_DATE_TIME)).status(BookingStatus.PENDING).build(),
                        BookingEntity.builder().roomId(1).title("Reunión mañanera").employeeId(2).startDateTime(ZonedDateTime.parse("2026-02-26T10:00:00Z", DateTimeFormatter.ISO_DATE_TIME)).status(BookingStatus.CANCELLED).build(),
                        BookingEntity.builder().roomId(1).title("Reunión mañanera").employeeId(1).startDateTime(ZonedDateTime.parse("2026-02-26T10:00:00Z", DateTimeFormatter.ISO_DATE_TIME)).status(BookingStatus.CONFIRMED).build(),
                        // Other room
                        BookingEntity.builder().roomId(2).title("Reunión mañanera").employeeId(1).startDateTime(ZonedDateTime.parse("2026-02-26T10:00:00Z", DateTimeFormatter.ISO_DATE_TIME)).status(BookingStatus.PENDING).build(),
                        BookingEntity.builder().roomId(2).title("Reunión mañanera").employeeId(2).startDateTime(ZonedDateTime.parse("2026-02-26T10:00:00Z", DateTimeFormatter.ISO_DATE_TIME)).status(BookingStatus.CANCELLED).build(),
                        BookingEntity.builder().roomId(2).title("Reunión mañanera").employeeId(2).startDateTime(ZonedDateTime.parse("2026-02-26T10:00:00Z", DateTimeFormatter.ISO_DATE_TIME)).status(BookingStatus.CONFIRMED).build()
                )
        );

        assertThat(jpaOutput.findAll(BookingSpecification.roomIdMatches(1).and(BookingSpecification.roomIdMatches(1).and(BookingSpecification.statusMatches(BookingStatus.CONFIRMED))), PageRequest.ofSize(10)))
                .hasSize(1);
    }

}
