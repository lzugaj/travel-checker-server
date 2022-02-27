package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.configuration.MapboxProperties;
import com.luv2code.travelchecker.service.impl.MapboxServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnableConfigurationProperties
public class MapboxServiceImplTest {

    @InjectMocks
    private MapboxServiceImpl mapboxService;

    @Mock
    private MapboxProperties mapboxProperties;

    private static final String TOKEN = "dsapodpmm32k1mpofjjfmpo213po21";

    @BeforeEach
    public void setup() {
        Mockito.when(mapboxProperties.getToken()).thenReturn(TOKEN);
    }

    @Test
    public void should_Fetch_Mapbox_Token() {
        final String mapboxToken = mapboxService.getToken();

        Assertions.assertNotNull(mapboxToken);
        Assertions.assertEquals(TOKEN, mapboxToken);
    }
}
