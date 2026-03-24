package io.github.katarem.application.port.output;

import io.github.katarem.domain.model.Room;

public interface RoomQueryPort {
    Room getRoomById(Integer roomId);
}
