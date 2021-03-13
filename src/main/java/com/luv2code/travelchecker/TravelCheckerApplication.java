package com.luv2code.travelchecker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class TravelCheckerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelCheckerApplication.class, args);
    }

}
