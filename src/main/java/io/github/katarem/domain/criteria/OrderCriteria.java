package io.github.katarem.domain.criteria;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderCriteria {
    private String field;
    private Direction direction;
}
