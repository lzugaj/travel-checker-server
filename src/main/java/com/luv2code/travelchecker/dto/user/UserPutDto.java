package com.luv2code.travelchecker.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPutDto {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("firstName")
    @NotBlank(message = "{user.firstName.blank}")
    @Size(min = 2, message = "{user.firstName.size}")
    private String firstName;

    @JsonProperty("lastName")
    @NotBlank(message = "{user.lastName.blank}")
    @Size(min = 2, message = "{user.lastName.size}")
    private String lastName;

    @JsonIgnore
    private LocalDateTime modifiedAt = LocalDateTime.now();

}
