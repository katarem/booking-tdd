package io.gihtub.katarem.infraestructure.adapter.input.rest.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SortingResponse {
    private String field;
    private String direction;
}
