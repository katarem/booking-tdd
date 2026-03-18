package io.gihtub.katarem.infraestructure.adapter.output.persistence;

import io.gihtub.katarem.application.port.output.BookingOutputPort;
import io.gihtub.katarem.domain.model.Booking;
import io.gihtub.katarem.domain.model.BookingStatus;
import io.gihtub.katarem.infraestructure.exception.impl.booking.BookingNotFoundException;
import io.gihtub.katarem.infraestructure.mapper.BookingPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
    public Booking createBooking(Booking booking) {
        var entity = output.save(mapper.toEntity(booking));
        return mapper.toDomain(entity);
    }

    @Override
    public Set<Booking> getAllBookingsByRoomId(Integer roomId) {
        var entities = output.findAllByRoomIdAndByStatusNot(roomId, BookingStatus.CANCELLED);
        return mapper.toDomain(entities);
    }
}
