package io.gihtub.katarem.infraestructure.adapter.output.client;

import io.gihtub.katarem.application.port.output.RoomQueryPort;
import io.gihtub.katarem.domain.model.Room;
import io.gihtub.katarem.infraestructure.mapper.RoomMapper;
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
                .orElseThrow(() -> new RuntimeException("Room " + roomId + " not found."));
        return mapper.toDomain(dto);
    }
}
