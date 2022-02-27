package com.luv2code.travelchecker.configuration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MapboxPropertiesTest {

    @InjectMocks
    private MapboxProperties mapboxProperties;

    @BeforeEach
    public void setup() {
        mapboxProperties = new MapboxProperties();
        mapboxProperties.setToken("dsadposandoinsaidnsoanodosandoan");
    }

    @Test
    public void should_Return_Mapbox_Token() {
        final String mapboxToken = mapboxProperties.getToken();

        Assertions.assertEquals("dsadposandoinsaidnsoanodosandoan", mapboxToken);
    }
}
