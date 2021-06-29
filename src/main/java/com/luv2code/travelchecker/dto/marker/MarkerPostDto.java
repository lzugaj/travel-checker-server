package com.luv2code.travelchecker.dto.marker;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.luv2code.travelchecker.domain.Coordinate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class MarkerPostDto {

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("eventDate")
    private LocalDate eventDate;

    @JsonProperty("grade")
    private Integer grade;

    @JsonProperty("should_visit_again")
    private Boolean shouldVisitAgain;

    @JsonProperty("coordinate")
    private Coordinate coordinate;

    @JsonIgnore
    private LocalDateTime createdAt = LocalDateTime.now();

}
