package io.gihtub.katarem.infraestructure.mapper;

import io.gihtub.katarem.domain.model.Room;
import io.gihtub.katarem.infraestructure.adapter.output.client.dto.RoomDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoomMapper {

    Room toDomain(RoomDto roomDto);

}
