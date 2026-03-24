package io.github.katarem.infraestructure.mapper;

import io.github.katarem.domain.model.Booking;
import io.github.katarem.infraestructure.adapter.output.persistence.BookingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookingPersistenceMapper {

    Booking toDomain(BookingEntity entity);

    BookingEntity toEntity(Booking domain);

    Set<Booking> toDomain(Set<BookingEntity> entities);

}
