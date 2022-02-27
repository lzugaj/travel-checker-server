package com.luv2code.travelchecker.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "luv2code.travel-checker.mapbox")
public class MapboxProperties {

    private String token;

}