package com.luv2code.travelchecker.dto.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.luv2code.travelchecker.validation.Email;
import com.luv2code.travelchecker.validation.Password;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationDto {

    @JsonProperty("email")
    @Email(message = "{user.email.invalid}")
    private String email;

    @JsonProperty("password")
    @NotBlank(message = "{user.password.blank}")
    @Password(message = "{user.password.invalid}")
    private String password;

}
