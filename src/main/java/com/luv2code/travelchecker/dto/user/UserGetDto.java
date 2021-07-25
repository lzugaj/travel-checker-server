package com.luv2code.travelchecker.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.luv2code.travelchecker.dto.marker.MarkerGetDto;
import com.luv2code.travelchecker.dto.role.RoleGetDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGetDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("username")
    private String username;

    @JsonProperty("roles")
    private List<RoleGetDto> roles;

    @JsonProperty("markers")
    private List<MarkerGetDto> markers;

}
