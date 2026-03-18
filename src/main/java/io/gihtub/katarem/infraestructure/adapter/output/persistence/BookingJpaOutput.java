package io.gihtub.katarem.infraestructure.adapter.output.persistence;

import io.gihtub.katarem.domain.model.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;
import java.util.UUID;

public interface BookingJpaOutput extends JpaRepository<BookingEntity, UUID> {

    Set<BookingEntity> findAllByRoomIdAndByStatusNot(Integer roomId, BookingStatus status);

}
