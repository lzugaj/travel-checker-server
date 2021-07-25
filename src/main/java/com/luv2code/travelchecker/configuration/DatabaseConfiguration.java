package com.luv2code.travelchecker.configuration;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Data
@Configuration
@ConfigurationProperties("spring.datasource")
public class DatabaseConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConfiguration.class);

    private String driverClassName;
    private String url;
    private String username;
    private String password;

    @Bean
    @Profile("dev")
    public void devDatabaseConnection() {
        LOGGER.info("DB connection for DEV - H2");
        LOGGER.info("Driver class name: {}", getDriverClassName());
        LOGGER.info("URL: {}", getUrl());
        LOGGER.info("Username: {}", getUsername());
        LOGGER.info("Password: {}", getPassword());
    }

    @Bean
    @Profile("test")
    public void testDatabaseConnection() {
        LOGGER.info("DB connection for TEST - MariaDB");
        LOGGER.info("Driver class name: {}", getDriverClassName());
        LOGGER.info("URL: {}", getUrl());
        LOGGER.info("Username: {}", getUsername());
        LOGGER.info("Password: {}", getPassword());
    }

    @Bean
    @Profile("prod")
    public void prodDatabaseConnection() {
        LOGGER.info("DB connection for PROD - MariaDB");
        LOGGER.info("Driver class name: {}", getDriverClassName());
        LOGGER.info("URL: {}", getUrl());
        LOGGER.info("Username: {}", getUsername());
        LOGGER.info("Password: {}", getPassword());
    }
}
