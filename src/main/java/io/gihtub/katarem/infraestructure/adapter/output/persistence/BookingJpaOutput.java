package io.gihtub.katarem.infraestructure.adapter.output.persistence;

import io.gihtub.katarem.domain.model.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Set;
import java.util.UUID;

public interface BookingJpaOutput extends JpaRepository<BookingEntity, UUID>, JpaSpecificationExecutor<BookingEntity> {

    Set<BookingEntity> findAllByRoomIdAndStatusNot(Integer roomId, BookingStatus status);

}
