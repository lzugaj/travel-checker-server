package com.luv2code.travelchecker.dto.marker;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.luv2code.travelchecker.dto.coordinate.CoordinateGetDto;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarkerGetDto {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("eventDate")
    private LocalDate eventDate;

    @JsonProperty("grade")
    private Integer grade;

    @JsonProperty("shouldVisitAgain")
    private Boolean shouldVisitAgain;

    @JsonProperty("coordinate")
    private CoordinateGetDto coordinate;

}
