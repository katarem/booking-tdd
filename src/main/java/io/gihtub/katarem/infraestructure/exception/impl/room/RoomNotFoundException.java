package io.gihtub.katarem.infraestructure.exception.impl.room;

import io.gihtub.katarem.infraestructure.exception.base.DomainNotFoundException;

public class RoomNotFoundException extends DomainNotFoundException {
    public RoomNotFoundException(Integer roomId) {
        super("Room", roomId.toString());
    }
}
