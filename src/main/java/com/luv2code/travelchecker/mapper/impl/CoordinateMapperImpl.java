package com.luv2code.travelchecker.mapper.impl;

import com.luv2code.travelchecker.domain.Coordinate;
import com.luv2code.travelchecker.dto.coordinate.CoordinateGetDto;
import com.luv2code.travelchecker.mapper.CoordinateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CoordinateMapperImpl implements CoordinateMapper {

    public static final Logger LOGGER = LoggerFactory.getLogger(CoordinateMapperImpl.class);

    @Override
    public CoordinateGetDto entityToDto(final Coordinate entity) {
        LOGGER.info("Start mapping Coordinate to CoordinateGetDto.");
        final CoordinateGetDto coordinateGetDto = new CoordinateGetDto();
        coordinateGetDto.setLongitude(entity.getLongitude());
        coordinateGetDto.setLatitude(entity.getLatitude());
        return coordinateGetDto;
    }

    @Override
    public List<CoordinateGetDto> entitiesToDto(final List<Coordinate> entities) {
        LOGGER.info("Start mapping Coordinate to CoordinateGetDto list.");
        final List<CoordinateGetDto> searchedDtoCoordinates = new ArrayList<>();
        for (Coordinate coordinate : entities) {
            searchedDtoCoordinates.add(entityToDto(coordinate));
        }

        return searchedDtoCoordinates;
    }
}
