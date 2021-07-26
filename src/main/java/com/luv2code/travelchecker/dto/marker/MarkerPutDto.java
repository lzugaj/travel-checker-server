package com.luv2code.travelchecker.dto.marker;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.luv2code.travelchecker.domain.Coordinate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MarkerPutDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    @NotBlank(message = "{marker.name.blank}")
    @Size(min = 2, message = "{marker.name.size}")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("eventDate")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate eventDate;

    @JsonProperty("grade")
    @NotNull(message = "{marker.grade.null}")
    private Integer grade;

    @JsonProperty("should_visit_again")
    @NotNull(message = "{marker.shouldVisitAgain.null}")
    private Boolean shouldVisitAgain;

    @JsonProperty("coordinate")
    private Coordinate coordinate;

    @JsonIgnore
    private LocalDateTime modifiedAt = LocalDateTime.now();

}