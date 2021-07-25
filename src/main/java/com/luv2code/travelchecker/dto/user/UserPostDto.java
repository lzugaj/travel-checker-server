package com.luv2code.travelchecker.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.luv2code.travelchecker.validation.Email;
import com.luv2code.travelchecker.validation.Password;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPostDto {

    @JsonProperty("firstName")
    @NotBlank(message = "{user.firstName.blank}")
    @Size(min = 2, message = "{user.firstName.size}")
    private String firstName;

    @JsonProperty("lastName")
    @NotBlank(message = "{user.lastName.blank}")
    @Size(min = 2, message = "{user.lastName.size}")
    private String lastName;

    @JsonProperty("email")
    @Email(message = "{user.email.invalid}")
    private String email;

    @JsonProperty("username")
    @NotBlank(message = "{user.username.blank}")
    @Size(min = 5, message = "{user.username.size}")
    private String username;

    @JsonProperty("password")
    @NotBlank(message = "{user.password.blank}")
    @Password(message = "{user.password.invalid}")
    private String password;

    @JsonIgnore
    private LocalDateTime createdAt = LocalDateTime.now();

}
