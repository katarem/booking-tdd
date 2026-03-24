package io.github.katarem.infraestructure.exception.impl.room;

import io.github.katarem.infraestructure.exception.base.DomainNotFoundException;

public class RoomNotFoundException extends DomainNotFoundException {
    public RoomNotFoundException(Integer roomId) {
        super("Room", roomId.toString());
    }
}
