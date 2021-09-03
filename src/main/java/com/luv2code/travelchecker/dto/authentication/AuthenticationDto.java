package com.luv2code.travelchecker.dto.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationDto {

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

}
