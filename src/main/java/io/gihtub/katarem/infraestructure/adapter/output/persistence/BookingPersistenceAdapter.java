package io.gihtub.katarem.infraestructure.adapter.output.persistence;

import io.gihtub.katarem.application.port.output.BookingOutputPort;
import io.gihtub.katarem.domain.model.Booking;
import io.gihtub.katarem.infraestructure.mapper.BookingPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class BookingPersistenceAdapter implements BookingOutputPort {

    private final BookingJpaOutput output;
    private final BookingPersistenceMapper mapper;

    @Override
    public Booking getBooking(UUID bookingId) {
        var entity = output.findById(bookingId)
                .orElseThrow(RuntimeException::new);
        return mapper.toDomain(entity);
    }
}
