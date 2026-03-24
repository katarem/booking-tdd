package io.github.katarem.infraestructure.adapter.output.client;

import io.github.katarem.application.port.output.EmployeeQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EmployeeOutputAdapter implements EmployeeQueryPort {

    private final EmployeeClient client;
    @Override
    public boolean existsById(Integer employeeId) {
        return client.existsById(employeeId);
    }
}
