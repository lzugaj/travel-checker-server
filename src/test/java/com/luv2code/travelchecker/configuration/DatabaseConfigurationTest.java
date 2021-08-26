package com.luv2code.travelchecker.configuration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DatabaseConfigurationTest {

    @InjectMocks
    private DatabaseConfiguration databaseConfiguration;

    @Test
    public void should_Return_Dev_Profile_Database_Configuration() {
        databaseConfiguration = new DatabaseConfiguration();
        databaseConfiguration.setDriverClassName("org.h2.Driver");
        databaseConfiguration.setUrl("jdbc:h2:mem:testdb");
        databaseConfiguration.setUsername("sa");
        databaseConfiguration.setPassword("");

        databaseConfiguration.devDatabaseConnection();

        Assertions.assertEquals("org.h2.Driver", databaseConfiguration.getDriverClassName());
        Assertions.assertEquals("jdbc:h2:mem:testdb", databaseConfiguration.getUrl());
        Assertions.assertEquals("sa", databaseConfiguration.getUsername());
        Assertions.assertEquals("", databaseConfiguration.getPassword());
    }

    @Test
    public void should_Return_Test_Profile_Database_Configuration() {
        databaseConfiguration = new DatabaseConfiguration();
        databaseConfiguration.setDriverClassName("org.mariadb.jdbc.Driver");
        databaseConfiguration.setUrl("jdbc:mariadb://localhost:3307/travelchecker-test");
        databaseConfiguration.setUsername("root");
        databaseConfiguration.setPassword("password");

        databaseConfiguration.testDatabaseConnection();

        Assertions.assertEquals("org.mariadb.jdbc.Driver", databaseConfiguration.getDriverClassName());
        Assertions.assertEquals("jdbc:mariadb://localhost:3307/travelchecker-test", databaseConfiguration.getUrl());
        Assertions.assertEquals("root", databaseConfiguration.getUsername());
        Assertions.assertEquals("password", databaseConfiguration.getPassword());
    }

    @Test
    public void should_Return_Prod_Profile_Database_Configuration() {
        databaseConfiguration = new DatabaseConfiguration();
        databaseConfiguration.setDriverClassName("org.mariadb.jdbc.Driver");
        databaseConfiguration.setUrl("jdbc:mariadb://localhost:3307/travelchecker-prod");
        databaseConfiguration.setUsername("root");
        databaseConfiguration.setPassword("password");

        databaseConfiguration.prodDatabaseConnection();

        Assertions.assertEquals("org.mariadb.jdbc.Driver", databaseConfiguration.getDriverClassName());
        Assertions.assertEquals("jdbc:mariadb://localhost:3307/travelchecker-prod", databaseConfiguration.getUrl());
        Assertions.assertEquals("root", databaseConfiguration.getUsername());
        Assertions.assertEquals("password", databaseConfiguration.getPassword());
    }
}
