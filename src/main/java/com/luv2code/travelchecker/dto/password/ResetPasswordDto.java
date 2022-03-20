package com.luv2code.travelchecker.dto.password;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.luv2code.travelchecker.validation.Password;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
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
