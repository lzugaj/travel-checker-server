package com.luv2code.travelchecker.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ConfigurationProperties("spring.datasource")
public class DatabaseConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConfiguration.class);

    private String driverClassName;
    private String url;
    private String username;
    private String password;

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Bean
    @Profile("dev")
    public void devDatabaseConnection() {
        LOGGER.info("DB connection for DEV - H2");
        LOGGER.info("Driver class name: {}", driverClassName);
        LOGGER.info("URL: {}", url);
        LOGGER.info("Username: {}", username);
        LOGGER.info("Password: {}", password);
    }

    @Bean
    @Profile("prod")
    public void prodDatabaseConnection() {
        LOGGER.info("DB connection for PROD - MariaDB");
        LOGGER.info("Driver class name: {}", driverClassName);
        LOGGER.info("URL: {}", url);
        LOGGER.info("Username: {}", username);
        LOGGER.info("Password: {}", password);
    }
}
