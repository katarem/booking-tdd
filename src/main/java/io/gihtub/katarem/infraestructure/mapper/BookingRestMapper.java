package io.gihtub.katarem.infraestructure.mapper;

import io.gihtub.katarem.domain.model.Booking;
import io.gihtub.katarem.infraestructure.adapter.input.rest.BookingRequest;
import io.gihtub.katarem.infraestructure.adapter.input.rest.CreateBookingResponse;
import io.gihtub.katarem.infraestructure.adapter.input.rest.GetBookingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookingRestMapper {

    GetBookingResponse toGetBookingResponse(Booking booking);

    Booking toDomain(BookingRequest request);

    CreateBookingResponse toCreateBookingResponse(Booking booking);

}
