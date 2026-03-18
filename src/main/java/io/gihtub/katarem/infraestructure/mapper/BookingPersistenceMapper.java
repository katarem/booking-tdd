package io.gihtub.katarem.infraestructure.mapper;

import io.gihtub.katarem.domain.model.Booking;
import io.gihtub.katarem.infraestructure.adapter.output.persistence.BookingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookingPersistenceMapper {

    Booking toDomain(BookingEntity entity);

    BookingEntity toEntity(Booking domain);

    Set<Booking> toDomain(Set<BookingEntity> entities);

}
