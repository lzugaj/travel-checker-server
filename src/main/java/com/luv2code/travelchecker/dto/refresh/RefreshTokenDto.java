package com.luv2code.travelchecker.dto.refresh;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenDto {

    @JsonProperty("refreshToken")
    @NotBlank(message = "{token.refreshToken.blank}")
    private String refreshToken;

}
