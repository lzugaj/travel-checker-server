package com.luv2code.travelchecker.configuration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MapboxConfigurationTest {

    @InjectMocks
    private MapboxConfiguration mapboxConfiguration;

    @BeforeEach
    public void setup() {
        mapboxConfiguration = new MapboxConfiguration();
        mapboxConfiguration.setToken("dsadposandoinsaidnsoanodosandoan");
    }

    @Test
    public void should_Return_Mapbox_Token() {
        final String mapboxToken = mapboxConfiguration.getToken();

        Assertions.assertEquals("dsadposandoinsaidnsoanodosandoan", mapboxToken);
    }
}
