package io.github.katarem.infraestructure.mapper;

import io.github.katarem.domain.model.Room;
import io.github.katarem.infraestructure.adapter.output.client.dto.RoomDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoomMapper {

    Room toDomain(RoomDto roomDto);

}
