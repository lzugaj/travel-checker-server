package com.luv2code.travelchecker.configuration;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "luv2code.travel-checker.security")
public class JwtConfiguration {

    private String secret;

    private Long expirationTime;

    private String tokenPrefix;

    private String headerName;

    private Integer bcryptRounds;

}
