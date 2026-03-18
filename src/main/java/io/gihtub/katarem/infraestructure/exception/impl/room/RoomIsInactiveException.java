package io.gihtub.katarem.infraestructure.exception.impl.room;

import io.gihtub.katarem.infraestructure.exception.base.DomainBusinessException;

public class RoomIsInactiveException extends DomainBusinessException {

    public RoomIsInactiveException(Integer id) {
        super("Room", "Room with id " + id + " is inactive");
    }
}
