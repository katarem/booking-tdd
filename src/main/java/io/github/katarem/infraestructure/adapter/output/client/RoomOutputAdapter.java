package io.github.katarem.infraestructure.adapter.output.client;

import io.github.katarem.application.port.output.RoomQueryPort;
import io.github.katarem.domain.model.Room;
import io.github.katarem.domain.exception.impl.room.RoomNotFoundException;
import io.github.katarem.infraestructure.mapper.RoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RoomOutputAdapter implements RoomQueryPort {

    private final RoomClient client;
    private final RoomMapper mapper;

    @Override
    public Room getRoomById(Integer roomId) {
        var dto = client.getRoomById(roomId)
                .orElseThrow(() -> new RoomNotFoundException(roomId));
        return mapper.toDomain(dto);
    }
}
