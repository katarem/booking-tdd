package io.gihtub.katarem.infraestructure.mapper;

import io.gihtub.katarem.domain.model.Booking;
import io.gihtub.katarem.infraestructure.adapter.input.rest.BookingRequest;
import io.gihtub.katarem.infraestructure.adapter.output.persistence.BookingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookingPersistenceMapper {

    Booking toDomain(BookingEntity entity);

    BookingEntity toEntity(Booking domain);

    Booking toDomain(BookingRequest request);

}
