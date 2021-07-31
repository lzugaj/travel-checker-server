package com.luv2code.travelchecker.dto.password;

import com.luv2code.travelchecker.validation.Password;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class PasswordUpdateDto {

    @NotBlank(message = "{user.password.blank}")
    @Password(message = "{user.password.invalid}")
    private String oldPassword;

    @NotBlank(message = "{user.password.blank}")
    @Password(message = "{user.password.invalid}")
    private String newPassword;

    @NotBlank(message = "{user.password.blank}")
    @Password(message = "{user.password.invalid}")
    private String confirmedNewPassword;

}
