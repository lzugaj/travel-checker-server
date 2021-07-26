package com.luv2code.travelchecker.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Data
@EnableSwagger2
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
    public Docket travelCheckerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(BASE_CONTROLLER_PACKAGE))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(getTitle())
                .description(getDescription())
                .version(getVersion())
                .termsOfServiceUrl(getTermsOfServiceUrl())
                .contact(contactInfo())
                .license(getLicense())
                .licenseUrl(getLicenseUrl())
                .build();
    }

    private Contact contactInfo() {
        return new Contact(
                getName(),
                getUrl(),
                getEmail()
        );
    }
}
