package com.luv2code.travelchecker.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "luv2code.travel-checker.jwt")
public class JwtProperties {

    private String secret;

    private Long expirationTime;

    private Long resetPasswordExpirationTime;

}
