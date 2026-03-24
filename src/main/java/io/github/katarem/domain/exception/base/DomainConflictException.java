package io.github.katarem.domain.exception.base;

public class DomainConflictException extends DomainBaseException {
    public DomainConflictException(String modelName, String message) {
        super(modelName, message);
    }
}
