package io.gihtub.katarem.domain.criteria;

import lombok.Data;

import java.util.Set;

@Data
public class PageCriteria {
    private Integer page;
    private Integer size;
    private Set<OrderCriteria> sort;
}
