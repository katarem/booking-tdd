package io.gihtub.katarem.infraestructure.adapter.output.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookingJpaOutput extends JpaRepository<BookingEntity, UUID> {
}
