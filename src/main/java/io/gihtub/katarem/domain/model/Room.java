package io.gihtub.katarem.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class Room {
    private Integer id;
    private Integer capacity;
    private boolean active;
}
