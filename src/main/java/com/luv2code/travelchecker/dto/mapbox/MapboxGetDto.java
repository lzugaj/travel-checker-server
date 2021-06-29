package com.luv2code.travelchecker.dto.mapbox;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MapboxGetDto {

    @JsonProperty("mapboxToken")
    private String mapboxToken;

}
