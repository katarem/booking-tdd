package io.gihtub.katarem.application.port.output;

import io.gihtub.katarem.domain.model.Room;

public interface RoomQueryPort {
    Room getRoomById(Integer roomId);
}
