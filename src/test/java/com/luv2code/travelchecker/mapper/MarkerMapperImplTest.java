package com.luv2code.travelchecker.mapper;

import com.luv2code.travelchecker.domain.Coordinate;
import com.luv2code.travelchecker.domain.Marker;
import com.luv2code.travelchecker.dto.coordinate.CoordinateGetDto;
import com.luv2code.travelchecker.dto.marker.MarkerGetDto;
import com.luv2code.travelchecker.dto.marker.MarkerPostDto;
import com.luv2code.travelchecker.dto.marker.MarkerPutDto;
import com.luv2code.travelchecker.mapper.impl.MarkerMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class MarkerMapperImplTest {

    @InjectMocks
    private MarkerMapperImpl markerMapper;

    @Mock
    private CoordinateMapper coordinateMapper;

    private Coordinate firstCoordinate;
    private Coordinate secondCoordinate;

    private CoordinateGetDto firstCoordinateGetDto;
    private CoordinateGetDto secondCoordinateGetDto;

    private Marker firstMarker;
    private Marker secondMarker;
    private Marker thirdMarker;

    private MarkerPostDto firstMarkerPostDto;

    private MarkerPutDto firstMarkerPutDto;

    private MarkerGetDto firstMarkerGetDto;
    private MarkerGetDto secondMarkerGetDto;

    private List<Marker> markers;
    private List<MarkerGetDto> dtoMarkers;

    @BeforeEach
    public void setup() {
        // Coordinate
        firstCoordinate = createCoordinate(1L, -0.127758, 51.507351);
        secondCoordinate = createCoordinate(2L, -74.005974, 40.712776);

        // CoordinateGetDto
        firstCoordinateGetDto = createCoordinateGetDto(firstCoordinate.getLongitude(), firstCoordinate.getLatitude());
        secondCoordinateGetDto = createCoordinateGetDto(secondCoordinate.getLongitude(), secondCoordinate.getLatitude());

        // Marker
        firstMarker = createMarker(1L, "London", "London is a busy and beautiful city", LocalDate.now(), 4, Boolean.TRUE, LocalDateTime.now(), null, firstCoordinate);
        secondMarker = createMarker(2L, "New York", "New York is too big city to explore", LocalDate.now(), 5, Boolean.FALSE, LocalDateTime.now(), null, secondCoordinate);
        thirdMarker = createMarker(1L, "Zagreb", "Zagreb is my home town", LocalDate.now(), 5, Boolean.TRUE, LocalDateTime.now(), null, firstCoordinate);

        // MarkerGetDto
        firstMarkerGetDto = createMarkerGetDto(firstMarker.getId(), firstMarker.getName(), firstMarker.getDescription(), firstMarker.getEventDate(), firstMarker.getGrade(), firstMarker.getShouldVisitAgain(), firstCoordinateGetDto);
        secondMarkerGetDto = createMarkerGetDto(secondMarker.getId(), secondMarker.getName(), secondMarker.getDescription(), secondMarker.getEventDate(), secondMarker.getGrade(), secondMarker.getShouldVisitAgain(), secondCoordinateGetDto);

        // MarkerPostDto
        firstMarkerPostDto = createMarkerPostDto(firstMarker.getName(), firstMarker.getDescription(), firstMarker.getEventDate(), firstMarker.getGrade(), firstMarker.getShouldVisitAgain(), firstMarker.getCoordinate(), LocalDateTime.now());

        // MarkerPutDto
        firstMarkerPutDto = createMarkerPutDto(firstMarker.getId(), thirdMarker.getName(), thirdMarker.getDescription(), thirdMarker.getEventDate(), thirdMarker.getGrade(), thirdMarker.getShouldVisitAgain(), firstMarker.getCoordinate());

        markers = Arrays.asList(firstMarker, secondMarker);
        dtoMarkers = Arrays.asList(firstMarkerGetDto, secondMarkerGetDto);

        Mockito.when(coordinateMapper.entityToDto(firstCoordinate)).thenReturn(firstCoordinateGetDto);
    }

    @Test
    public void should_Return_Marker_When_MarkerPostDto_Correctly_Mapped() {
        final Marker marker = markerMapper.dtoToEntity(firstMarkerPostDto);

        Assertions.assertNotNull(marker);
        Assertions.assertEquals("London", marker.getName());
    }

    @Test
    public void should_Return_MarkerGetDto_When_Marker_Correctly_Mapped() {
        final MarkerGetDto markerGetDto = markerMapper.entityToDto(firstMarker);

        Assertions.assertNotNull(markerGetDto);
        Assertions.assertEquals("1", String.valueOf(markerGetDto.getId()));
    }

    @Test
    public void should_Return_Marker_When_MarkerPutDto_Correctly_Mapped() {
        final Marker marker = markerMapper.dtoToEntity(firstMarker, firstMarkerPutDto);

        Assertions.assertNotNull(marker);
        Assertions.assertEquals("Zagreb", marker.getName());
    }

    @Test
    public void should_Return_MarkerGetDto_List_When_Markers_Correctly_Mapped() {
        final List<MarkerGetDto> dtoMarkers = markerMapper.entitiesToDto(markers);

        Assertions.assertNotNull(dtoMarkers);
        Assertions.assertEquals(2, dtoMarkers.size());
    }

    private Coordinate createCoordinate(final Long id, final Double longitude, final Double latitude) {
        final Coordinate coordinate = new Coordinate();
        coordinate.setId(id);
        coordinate.setLongitude(longitude);
        coordinate.setLatitude(latitude);
        return coordinate;
    }

    private CoordinateGetDto createCoordinateGetDto(final Double longitude, final Double latitude) {
        final CoordinateGetDto coordinateGetDto = new CoordinateGetDto();
        coordinateGetDto.setLongitude(longitude);
        coordinateGetDto.setLatitude(latitude);
        return coordinateGetDto;
    }

    private Marker createMarker(final Long id, final String name, final String description, final LocalDate eventDate, final Integer grade, final Boolean shouldVisitAgain, final LocalDateTime createdAt, final LocalDateTime modifiedAt, final Coordinate coordinate) {
        final Marker marker = new Marker();
        marker.setId(id);
        marker.setName(name);
        marker.setDescription(description);
        marker.setEventDate(eventDate);
        marker.setGrade(grade);
        marker.setShouldVisitAgain(shouldVisitAgain);
        marker.setCreatedAt(createdAt);
        marker.setModifiedAt(modifiedAt);
        marker.setCoordinate(coordinate);
        marker.setUsers(null);
        return marker;
    }

    private MarkerGetDto createMarkerGetDto(final Long id, final String name, final String description, final LocalDate eventDate, final Integer grade, final Boolean shouldVisitAgain, final CoordinateGetDto coordinateGetDto) {
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

    private MarkerPostDto createMarkerPostDto(final String name, final String description, final LocalDate eventDate, final Integer grade, final Boolean shouldVisitAgain, final Coordinate coordinate, final LocalDateTime createdAt) {
        final MarkerPostDto markerPostDto = new MarkerPostDto();
        markerPostDto.setName(name);
        markerPostDto.setDescription(description);
        markerPostDto.setEventDate(eventDate);
        markerPostDto.setGrade(grade);
        markerPostDto.setShouldVisitAgain(shouldVisitAgain);
        markerPostDto.setCoordinate(coordinate);
        markerPostDto.setCreatedAt(createdAt);
        return markerPostDto;
    }

    private MarkerPutDto createMarkerPutDto(final Long id, final String name, final String description, final LocalDate eventDate, final int grade, final Boolean shouldVisitAgain, final Coordinate coordinate) {
        final MarkerPutDto markerPutDto = new MarkerPutDto();
        markerPutDto.setId(id);
        markerPutDto.setName(name);
        markerPutDto.setDescription(description);
        markerPutDto.setEventDate(eventDate);
        markerPutDto.setGrade(grade);
        markerPutDto.setShouldVisitAgain(shouldVisitAgain);
        markerPutDto.setCoordinate(coordinate);
        return markerPutDto;
    }
}
