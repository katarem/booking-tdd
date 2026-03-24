package io.github.katarem.domain.exception.base;

public abstract class DomainBusinessException extends DomainBaseException {
    public DomainBusinessException(String modelName, String message) {
        super(modelName.toUpperCase() + "_BUSINESS_RULE_VIOLATION", message);
    }
}
