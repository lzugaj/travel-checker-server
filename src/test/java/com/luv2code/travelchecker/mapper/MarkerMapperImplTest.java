package com.luv2code.travelchecker.mapper;

import com.luv2code.travelchecker.domain.Coordinate;
import com.luv2code.travelchecker.domain.Marker;
import com.luv2code.travelchecker.dto.coordinate.CoordinateGetDto;
import com.luv2code.travelchecker.dto.coordinate.CoordinatePostDto;
import com.luv2code.travelchecker.dto.marker.MarkerGetDto;
import com.luv2code.travelchecker.dto.marker.MarkerPostDto;
import com.luv2code.travelchecker.dto.marker.MarkerPutDto;
import com.luv2code.travelchecker.mapper.impl.MarkerMapperImpl;
import com.luv2code.travelchecker.utils.CoordinateUtil;
import com.luv2code.travelchecker.utils.MarkerUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
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

    @Mock
    private ModelMapper modelMapper;

    private Marker firstMarker;
    private Marker secondMarker;

    private MarkerPostDto firstMarkerPostDto;

    private MarkerPutDto firstMarkerPutDto;

    private List<Marker> markers;

    @BeforeEach
    public void setup() {
        // Coordinate
        Coordinate firstCoordinate = CoordinateUtil.createCoordinate(1L, -0.127758, 51.507351);
        Coordinate secondCoordinate = CoordinateUtil.createCoordinate(2L, -74.005974, 40.712776);

        // CoordinateGetDto
        CoordinateGetDto firstCoordinateGetDto = CoordinateUtil.createCoordinateGetDto(firstCoordinate.getId(), firstCoordinate.getLongitude(), firstCoordinate.getLatitude());
        CoordinateGetDto secondCoordinateGetDto = CoordinateUtil.createCoordinateGetDto(secondCoordinate.getId(), secondCoordinate.getLongitude(), secondCoordinate.getLatitude());

        CoordinatePostDto firstCoordinatePostDto = CoordinateUtil.createCoordinatePostDto(firstCoordinate.getLongitude(), firstCoordinate.getLatitude());

        // Marker
        firstMarker = MarkerUtil.createMarker(1L, "London", "London is a busy and beautiful city", LocalDate.now(), 4, Boolean.TRUE, LocalDateTime.now(), firstCoordinate);
        secondMarker = MarkerUtil.createMarker(2L, "New York", "New York is too big city to explore", LocalDate.now(), 5, Boolean.FALSE, LocalDateTime.now(), secondCoordinate);
        Marker thirdMarker = MarkerUtil.createMarker(1L, "Zagreb", "Zagreb is my home town", LocalDate.now(), 5, Boolean.TRUE, LocalDateTime.now(), firstCoordinate);

        // MarkerGetDto
        MarkerGetDto firstMarkerGetDto = MarkerUtil.createMarkerGetDto(firstMarker.getId(), firstMarker.getName(), firstMarker.getDescription(), firstMarker.getEventDate(), firstMarker.getGrade(), firstMarker.getShouldVisitAgain(), firstCoordinateGetDto);
        MarkerGetDto secondMarkerGetDto = MarkerUtil.createMarkerGetDto(secondMarker.getId(), secondMarker.getName(), secondMarker.getDescription(), secondMarker.getEventDate(), secondMarker.getGrade(), secondMarker.getShouldVisitAgain(), secondCoordinateGetDto);

        // MarkerPostDto
        firstMarkerPostDto = MarkerUtil.createMarkerPostDto(firstMarker.getName(), firstMarker.getDescription(), firstMarker.getEventDate(), firstMarker.getGrade(), firstMarker.getShouldVisitAgain(), firstCoordinatePostDto, LocalDateTime.now());

        // MarkerPutDto
        firstMarkerPutDto = MarkerUtil.createMarkerPutDto(firstMarker.getId(), thirdMarker.getName(), thirdMarker.getDescription(), thirdMarker.getEventDate(), thirdMarker.getGrade(), thirdMarker.getShouldVisitAgain(), firstMarker.getCoordinate());

        markers = Arrays.asList(firstMarker, secondMarker);
        List<MarkerGetDto> dtoMarkers = Arrays.asList(firstMarkerGetDto, secondMarkerGetDto);

        Mockito.when(coordinateMapper.entityToDto(secondCoordinate)).thenReturn(secondMarkerGetDto.getCoordinate());

        Mockito.when(modelMapper.map(firstMarkerPostDto, Marker.class)).thenReturn(firstMarker);
        Mockito.when(modelMapper.map(firstMarker, MarkerGetDto.class)).thenReturn(firstMarkerGetDto);
        Mockito.when(modelMapper.map(secondMarker, MarkerGetDto.class)).thenReturn(secondMarkerGetDto);

        Mockito.when(modelMapper.map(firstMarkerPutDto, Marker.class)).thenReturn(firstMarker);
    }

    @Test
    @DisplayName("dtoToEntity(MarkerPostDto) - should return Marker")
    public void should_Return_Marker_When_MarkerPostDto_Correctly_Mapped() {
        final Marker newMarker = markerMapper.dtoToEntity(firstMarkerPostDto);

        Assertions.assertNotNull(newMarker);
        Assertions.assertEquals("1", String.valueOf(newMarker.getId()));
    }

    @Test
    @DisplayName("entityToDto(Marker) - should return MarkerGetDto")
    public void should_Return_MarkerGetDto_When_Marker_Correctly_Mapped() {
        final MarkerGetDto searchedMarkerDto = markerMapper.entityToDto(secondMarker);

        Assertions.assertNotNull(searchedMarkerDto);
        Assertions.assertEquals("2", String.valueOf(searchedMarkerDto.getId()));
    }

    @Test
    @DisplayName("dtoToEntity(Marker, MarkerPutDto) - should return Marker")
    public void should_Return_Marker_When_MarkerPutDto_Correctly_Mapped() {
        final Marker updatedMarker = markerMapper.dtoToEntity(firstMarker, firstMarkerPutDto);

        Assertions.assertNotNull(updatedMarker);
        Assertions.assertEquals("Zagreb", updatedMarker.getName());
    }

    @Test
    @DisplayName("entitiesToDto(List<Marker>) - should return MarkerGetDto list")
    public void should_Return_MarkerGetDto_List_When_Markers_Correctly_Mapped() {
        final List<MarkerGetDto> searchedDtoMarkers = markerMapper.entitiesToDto(markers);

        Assertions.assertNotNull(searchedDtoMarkers);
        Assertions.assertEquals(2, searchedDtoMarkers.size());
    }
}
