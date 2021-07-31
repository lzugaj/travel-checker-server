package com.luv2code.travelchecker.mapper.impl;

import com.luv2code.travelchecker.dto.mapbox.MapboxGetDto;
import com.luv2code.travelchecker.mapper.MapboxMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MapboxMapperImpl implements MapboxMapper<MapboxGetDto> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapboxMapperImpl.class);

    @Override
    public MapboxGetDto entityToDto(final String token) {
        LOGGER.info("Start mapping String to MapboxGetDto.");
        return MapboxGetDto.builder()
                .mapboxToken(token)
                .build();
    }
}
