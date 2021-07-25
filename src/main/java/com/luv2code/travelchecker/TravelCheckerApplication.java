package com.luv2code.travelchecker;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
@OpenAPIDefinition(
        info = @Info(
                title = "Travel Checker API",
                version = "1.0.0",
                contact = @Contact(
                        name = "Luka Zugaj",
                        email = "luka.zugaj7@gmail.com",
                        url = "https://github.com/lzugaj"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                )
        )
)
public class TravelCheckerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelCheckerApplication.class, args);
    }

}
