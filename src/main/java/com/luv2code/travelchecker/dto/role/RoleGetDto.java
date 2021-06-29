package com.luv2code.travelchecker.dto.role;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleGetDto {

    @JsonProperty("name")
    private String name;

}
