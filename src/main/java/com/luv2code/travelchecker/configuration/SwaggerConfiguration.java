package com.luv2code.travelchecker.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "luv2code.travel-checker.api-info")
public class SwaggerConfiguration {

    public static final String BASE_CONTROLLER_PACKAGE = "com.luv2code.travelchecker.controller";

    private String title;
    private String description;
    private String version;
    private String termsOfServiceUrl;
    private String license;
    private String licenseUrl;
    private String name;
    private String url;
    private String email;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(title)
                        .version(version)
                        .description(description)
                        .termsOfService(termsOfServiceUrl)
                        .contact(contactInfo())
                        .license(licenseInfo()));
    }

    private Contact contactInfo() {
        return new Contact()
                .email(email)
                .name(name)
                .url(url);
    }

    private License licenseInfo() {
        return new License()
                .name(license)
                .url(licenseUrl);
    }
}
