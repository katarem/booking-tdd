package io.github.katarem.infraestructure.exception.impl.room;

import io.github.katarem.infraestructure.exception.base.DomainBusinessException;

public class RoomIsInactiveException extends DomainBusinessException {

    public RoomIsInactiveException(Integer id) {
        super("Room", "Room with id " + id + " is inactive");
    }
}
