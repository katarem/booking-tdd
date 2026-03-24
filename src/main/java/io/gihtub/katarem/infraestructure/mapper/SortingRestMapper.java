package io.gihtub.katarem.infraestructure.mapper;

import io.gihtub.katarem.domain.criteria.OrderCriteria;
import io.gihtub.katarem.infraestructure.adapter.input.rest.response.SortingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SortingRestMapper {


    SortingResponse toResponse(OrderCriteria orderCriteria);

}
