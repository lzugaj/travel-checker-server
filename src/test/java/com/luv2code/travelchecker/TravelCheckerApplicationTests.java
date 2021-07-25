package com.luv2code.travelchecker;

import com.luv2code.travelchecker.configuration.MapboxConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnableConfigurationProperties(MapboxConfiguration.class)
class TravelCheckerApplicationTests {

    @Test
    void contextLoads() {
        TravelCheckerApplication.main(new String[] {});
    }
}
