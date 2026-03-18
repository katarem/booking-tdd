package io.gihtub.katarem.infraestructure.exception.base;

import lombok.Getter;

@Getter
public abstract class DomainBaseException extends RuntimeException {

    private final String code;

    public DomainBaseException(String code, String message) {
        super(message);
        this.code = code;
    }
}
