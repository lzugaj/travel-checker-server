package com.luv2code.travelchecker.configuration;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "luv2code.travel-checker.locale")
public class LocaleConfiguration {

    private String baseName;

    private String defaultLocale;

    private String defaultEncoding;

}
