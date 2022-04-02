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

    public static final String DRIVER_CLASS_NAME = "Driver class name: {}";
    public static final String DATABASE_URL = "URL: {}";
    public static final String DATABASE_USERNAME = "Username: {}";
    public static final String DATABASE_PASSWORD = "Password: {}";

    private String driverClassName;
    private String url;
    private String username;
    private String password;

    @Bean
    @Profile("dev")
    public void devDatabaseConnection() {
        LOGGER.info("DB connection for DEV - H2");
        LOGGER.info(DRIVER_CLASS_NAME, getDriverClassName());
        LOGGER.info(DATABASE_URL, getUrl());
        LOGGER.info(DATABASE_USERNAME, getUsername());
        LOGGER.info(DATABASE_PASSWORD, getPassword());
    }

    @Bean
    @Profile("test")
    public void testDatabaseConnection() {
        LOGGER.info("DB connection for TEST - MariaDB");
        LOGGER.info(DRIVER_CLASS_NAME, getDriverClassName());
        LOGGER.info(DATABASE_URL, getUrl());
        LOGGER.info(DATABASE_USERNAME, getUsername());
        LOGGER.info(DATABASE_PASSWORD, getPassword());
    }

    @Bean
    @Profile("prod")
    public void prodDatabaseConnection() {
        LOGGER.info("DB connection for PROD - Postgres");
        LOGGER.info(DRIVER_CLASS_NAME, getDriverClassName());
        LOGGER.info(DATABASE_URL, getUrl());
        LOGGER.info(DATABASE_USERNAME, getUsername());
        LOGGER.info(DATABASE_PASSWORD, getPassword());
    }
}
