package io.gihtub.katarem.infraestructure.adapter.output.client;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeClient {

    private final List<Integer> employees = List.of(1,2,3,4,5,6,7,8,9,10);

    public boolean existsById(Integer employeeId) {
        return employees.contains(employeeId);
    }

}
