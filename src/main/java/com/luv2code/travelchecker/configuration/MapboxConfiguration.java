package com.luv2code.travelchecker.configuration;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Builder
@ToString
@Configuration
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "luv2code.travel-checker.mapbox")
public class MapboxConfiguration {

    private String token;

}
