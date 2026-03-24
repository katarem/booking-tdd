package io.github.katarem.domain.exception.impl.employee;

import io.github.katarem.domain.exception.base.DomainBusinessException;

public class EmployeeIsNotActiveException extends DomainBusinessException {
    public EmployeeIsNotActiveException(Integer id) {
        super("Employee", "Employee " + id + " is not active.");
    }
}
