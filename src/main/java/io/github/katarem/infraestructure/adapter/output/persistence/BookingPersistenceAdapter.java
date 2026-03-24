package io.github.katarem.infraestructure.adapter.output.persistence;

import io.github.katarem.application.port.output.BookingOutputPort;
import io.github.katarem.domain.criteria.BookingCriteria;
import io.github.katarem.domain.criteria.PageCriteria;
import io.github.katarem.domain.model.Booking;
import io.github.katarem.domain.model.BookingList;
import io.github.katarem.domain.model.BookingStatus;
import io.github.katarem.domain.exception.impl.booking.BookingNotFoundException;
import io.github.katarem.infraestructure.mapper.BookingPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class BookingPersistenceAdapter implements BookingOutputPort {

    private final BookingJpaOutput output;
    private final BookingPersistenceMapper mapper;

    @Override
    public Booking getBooking(UUID bookingId) {
        var entity = output.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException(bookingId));
        return mapper.toDomain(entity);
    }

    @Override
    public Booking upsertBooking(Booking booking) {
        var entity = output.save(mapper.toEntity(booking));
        return mapper.toDomain(entity);
    }

    @Override
    public Set<Booking> getAllBookingsByRoomId(Integer roomId) {
        var entities = output.findAllByRoomIdAndStatusNot(roomId, BookingStatus.CANCELLED);
        return mapper.toDomain(entities);
    }

    @Override
    public BookingList getBookings(BookingCriteria bookingCriteria, PageCriteria pageCriteria) {

        Specification<BookingEntity> specification = Specification.unrestricted();

        if(!Objects.isNull(bookingCriteria.getRoomId())) {
            specification = specification.and(BookingSpecification.roomIdMatches(bookingCriteria.getEmployeeId()));
        }

        if(!Objects.isNull(bookingCriteria.getEmployeeId())) {
            specification = specification.and(BookingSpecification.employeeIdMatches(bookingCriteria.getEmployeeId()));
        }

        if(!Objects.isNull(bookingCriteria.getDate())) {
            specification = specification.and(BookingSpecification.dateMatches(bookingCriteria.getDate()));
        }

        if(!Objects.isNull(bookingCriteria.getStatus())) {
            specification = specification.and(BookingSpecification.statusMatches(bookingCriteria.getStatus()));
        }

        var sort = Sort.by(pageCriteria.getSort().stream().map(crit ->
                new Sort.Order(Sort.Direction.valueOf(crit.getDirection().name()), crit.getField())).toList());

        var pageRequest = PageRequest.of(pageCriteria.getPage(), pageCriteria.getSize(), sort);

        var page = output.findAll(specification, pageRequest);

        return BookingList.builder()
                .bookings(mapper.toDomain(page.toSet()))
                .size(page.getSize())
                .page(page.getNumber())
                .totalPages(page.getTotalPages())
                .sort(pageCriteria.getSort())
                .build();
    }
}
