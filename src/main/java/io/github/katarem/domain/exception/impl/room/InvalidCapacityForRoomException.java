package io.github.katarem.domain.exception.impl.room;

import io.github.katarem.domain.exception.base.DomainBusinessException;

public class InvalidCapacityForRoomException extends DomainBusinessException {
    public InvalidCapacityForRoomException(Integer id) {
        super("Room", "Invalid attendee amount for room " + id);
    }
}
