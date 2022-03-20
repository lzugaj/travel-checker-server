package com.luv2code.travelchecker.mock;

import com.luv2code.travelchecker.domain.Coordinate;
import com.luv2code.travelchecker.domain.Marker;
import com.luv2code.travelchecker.dto.coordinate.CoordinateGetDto;
import com.luv2code.travelchecker.dto.coordinate.CoordinatePostDto;
import com.luv2code.travelchecker.dto.marker.MarkerGetDto;
import com.luv2code.travelchecker.dto.marker.MarkerPostDto;
import com.luv2code.travelchecker.dto.marker.MarkerPutDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class MarkerMock {

    public static Marker createMarker(final UUID id, final String name, final String description, final LocalDate eventDate, final Integer grade, final Boolean shouldVisitAgain, final LocalDateTime createdAt, final Coordinate coordinate) {
        final Marker marker = new Marker();
        marker.setId(id);
        marker.setName(name);
        marker.setDescription(description);
        marker.setEventDate(eventDate);
        marker.setGrade(grade);
        marker.setShouldVisitAgain(shouldVisitAgain);
        marker.setCreatedAt(createdAt);
        marker.setModifiedAt(null);
        marker.setCoordinate(coordinate);
        return marker;
    }

    public static MarkerGetDto createMarkerGetDto(final UUID id, final String name, final String description, final LocalDate eventDate, final Integer grade, final Boolean shouldVisitAgain, final CoordinateGetDto coordinateGetDto) {
        final MarkerGetDto markerGetDto = new MarkerGetDto();
        markerGetDto.setId(id);
        markerGetDto.setName(name);
        markerGetDto.setDescription(description);
        markerGetDto.setEventDate(eventDate);
        markerGetDto.setGrade(grade);
        markerGetDto.setShouldVisitAgain(shouldVisitAgain);
        markerGetDto.setCoordinate(coordinateGetDto);
        return markerGetDto;
    }

    public static MarkerPostDto createMarkerPostDto(final String name, final String description, final LocalDate eventDate, final Integer grade, final Boolean shouldVisitAgain, final CoordinatePostDto coordinatePostDto, final LocalDateTime createdAt) {
        final MarkerPostDto markerPostDto = new MarkerPostDto();
        markerPostDto.setName(name);
        markerPostDto.setDescription(description);
        markerPostDto.setEventDate(eventDate);
        markerPostDto.setGrade(grade);
        markerPostDto.setShouldVisitAgain(shouldVisitAgain);
        markerPostDto.setCoordinatePostDto(coordinatePostDto);
        markerPostDto.setCreatedAt(createdAt);
        return markerPostDto;
    }

    public static MarkerPutDto createMarkerPutDto(final UUID id, final String name, final String description, final LocalDate eventDate, final int grade, final Boolean shouldVisitAgain, final Coordinate coordinate) {
        final MarkerPutDto markerPutDto = new MarkerPutDto();
        markerPutDto.setId(id);
        markerPutDto.setName(name);
        markerPutDto.setDescription(description);
        markerPutDto.setEventDate(eventDate);
        markerPutDto.setGrade(grade);
        markerPutDto.setShouldVisitAgain(shouldVisitAgain);
        /*markerPutDto.setCoordinate(coordinate);*/
        return markerPutDto;
    }
}
