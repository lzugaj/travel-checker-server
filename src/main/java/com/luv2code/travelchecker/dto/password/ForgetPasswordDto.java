package com.luv2code.travelchecker.dto.password;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.luv2code.travelchecker.validation.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForgetPasswordDto {

    @JsonProperty("email")
    @Email(message = "{user.email.invalid}")
    private String email;

}
