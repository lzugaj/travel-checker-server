package com.luv2code.travelchecker.mapper;

import com.luv2code.travelchecker.dto.mapbox.MapboxGetDto;
import com.luv2code.travelchecker.mapper.impl.MapboxMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MapboxMapperImplTest {

    @InjectMocks
    private MapboxMapperImpl mapboxMapper;

    @Test
    public void test() {
        String mapboxToken = "nsoiandaoiNoin321321ndoisand";
        MapboxGetDto mapboxGetDto = mapboxMapper.entityToDto(mapboxToken);

        Assertions.assertNotNull(mapboxGetDto);
        Assertions.assertEquals(mapboxToken, mapboxGetDto.getMapboxToken());
    }
}
