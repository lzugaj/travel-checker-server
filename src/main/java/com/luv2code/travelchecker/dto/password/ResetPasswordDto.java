package com.luv2code.travelchecker.dto.password;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.luv2code.travelchecker.validation.Password;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordDto {

    @JsonProperty("newPassword")
    @NotBlank(message = "{user.password.blank}")
    @Password(message = "{user.password.invalid}")
    private String newPassword;

    @JsonProperty("confirmedNewPassword")
    @NotBlank(message = "{user.password.blank}")
    @Password(message = "{user.password.invalid}")
    private String confirmedNewPassword;

}
