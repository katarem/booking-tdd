package io.github.katarem.infraestructure.mapper;

import io.github.katarem.domain.criteria.OrderCriteria;
import io.github.katarem.infraestructure.adapter.input.rest.response.SortingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SortingRestMapper {


    SortingResponse toResponse(OrderCriteria orderCriteria);

}
