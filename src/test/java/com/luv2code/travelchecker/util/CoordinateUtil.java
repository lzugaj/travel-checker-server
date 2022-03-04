package com.luv2code.travelchecker.util;

import com.luv2code.travelchecker.domain.Coordinate;
import com.luv2code.travelchecker.dto.coordinate.CoordinateGetDto;
import com.luv2code.travelchecker.dto.coordinate.CoordinatePostDto;

import java.util.UUID;

public class CoordinateUtil {

    public static Coordinate createCoordinate(final UUID id, final Double longitude, final Double latitude) {
        final Coordinate coordinate = new Coordinate();
        coordinate.setId(id);
        coordinate.setLongitude(longitude);
        coordinate.setLatitude(latitude);
        return coordinate;
    }

    public static CoordinateGetDto createCoordinateGetDto(final UUID id, final Double longitude, final Double latitude) {
        final CoordinateGetDto coordinateGetDto = new CoordinateGetDto();
        coordinateGetDto.setId(id);
        coordinateGetDto.setLongitude(longitude);
        coordinateGetDto.setLatitude(latitude);
        return coordinateGetDto;
    }

    public static CoordinatePostDto createCoordinatePostDto(final Double longitude, final Double latitude) {
        final CoordinatePostDto coordinatePostDto = new CoordinatePostDto();
        coordinatePostDto.setLongitude(longitude);
        coordinatePostDto.setLatitude(latitude);
        return coordinatePostDto;
    }
}
