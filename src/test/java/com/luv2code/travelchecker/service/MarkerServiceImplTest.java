package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.domain.Coordinate;
import com.luv2code.travelchecker.domain.Marker;
import com.luv2code.travelchecker.dto.marker.MarkerPostDto;
import com.luv2code.travelchecker.dto.marker.MarkerPutDto;
import com.luv2code.travelchecker.dto.user.UserGetDto;
import com.luv2code.travelchecker.exception.EntityNotFoundException;
import com.luv2code.travelchecker.mapper.MarkerMapper;
import com.luv2code.travelchecker.repository.MarkerRepository;
import com.luv2code.travelchecker.service.impl.MarkerServiceImpl;
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
import java.util.Optional;

@SpringBootTest
public class MarkerServiceImplTest {

    @InjectMocks
    private MarkerServiceImpl markerService;

    @Mock
    private MarkerRepository markerRepository;

    @Mock
    private MarkerMapper markerMapper;

    private Coordinate firstCoordinate;
    private Coordinate secondCoordinate;
    private Coordinate thirdCoordinate;

    private Marker firstMarker;
    private Marker secondMarker;
    private Marker thirdMarker;
    private Marker fourthMarker;

    private MarkerPostDto firstMarkerPostDto;

    private MarkerPutDto firstMarkerPutDto;

    private UserGetDto firstUserGetDto;

    private List<Marker> markers;

    @BeforeEach
    public void setup() {
        // Coordinate
        firstCoordinate = createCoordinate(1L, 15.966568111, 45.815399222);
        secondCoordinate = createCoordinate(2L, 15.242222412, 44.119722345);
        thirdCoordinate = createCoordinate(3L, 18.092162345, 42.648071234);

        // Marker
        firstMarker = createMarker(1L, "Zagreb", "Zagreb was beautiful destination", LocalDate.of(2021, 3, 11), 4, Boolean.TRUE, firstCoordinate);
        secondMarker = createMarker(2L, "Split", "Split is nice but there are too much tourists", LocalDate.of(2020, 8, 1), 3, Boolean.FALSE, secondCoordinate);
        thirdMarker = createMarker(3L, "Dubrovnik", "Dubrovnik is like a paradise", (LocalDate.of(2020, 8, 1)), 5, Boolean.TRUE, thirdCoordinate);
        fourthMarker = createMarker(1L, thirdMarker.getName(), thirdMarker.getDescription(), firstMarker.getEventDate(), secondMarker.getGrade(), firstMarker.getShouldVisitAgain(), firstMarker.getCoordinate());

        markers = Arrays.asList(secondMarker, thirdMarker);

        // MarkerPostDto
        firstMarkerPostDto = createMarkerPostDto(firstMarker.getName(), firstMarker.getDescription(), firstMarker.getEventDate(), firstMarker.getGrade(), firstMarker.getShouldVisitAgain(), firstMarker.getCoordinate());

        // MarkerPutDto
        firstMarkerPutDto = createMarkerPutDto(fourthMarker.getId(), fourthMarker.getName(), fourthMarker.getDescription(), fourthMarker.getEventDate(), fourthMarker.getGrade(), fourthMarker.getShouldVisitAgain(), fourthMarker.getCoordinate());;

        // UserGetDto
        firstUserGetDto = createUserGetDto(1L, "Eunice", "Holt", "eholt@gmail.com", "Mone1968");

        Mockito.when(markerMapper.dtoToEntity(firstMarkerPostDto)).thenReturn(firstMarker);
        Mockito.when(markerMapper.dtoToEntity(firstMarker, firstMarkerPutDto)).thenReturn(fourthMarker);

        Mockito.when(markerRepository.save(firstMarker)).thenReturn(firstMarker);
        Mockito.when(markerRepository.findById(secondMarker.getId())).thenReturn(java.util.Optional.ofNullable(secondMarker));
        Mockito.when(markerRepository.findAll()).thenReturn(markers);
    }

    @Test
    public void should_Save_Marker_When_Everything_Is_Entered_Correctly() {
        final Marker newMarker = markerService.save(firstUserGetDto, firstMarkerPostDto);

        Assertions.assertNotNull(newMarker);
        Assertions.assertEquals("1", String.valueOf(newMarker.getId()));
    }

    @Test
    public void should_Return_Marker_When_Id_Is_Valid() {
        final Marker searchedMaker = markerService.findById(secondMarker.getId());

        Assertions.assertNotNull(searchedMaker);
        Assertions.assertEquals("2", String.valueOf(searchedMaker.getId()));
    }

    @Test
    public void should_Throw_Exception_When_Id_Not_Founded() {
        Mockito.when(markerRepository.findById(firstMarker.getId()))
                .thenReturn(Optional.empty());

        final Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> markerService.findById(firstMarker.getId())
        );

        final String expectedMessage = "Entity 'Marker' with 'id' value '1' not founded.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void should_Return_All_Markers() {
        final List<Marker> markers = markerService.findAll();

        Assertions.assertNotNull(markers);
        Assertions.assertEquals(2, markers.size());
    }

    @Test
    public void should_Update_Marker_When_Everything_Is_Entered_Correctly() {
        final Marker updatedMarker = markerService.update(firstMarker, firstMarkerPutDto);

        Assertions.assertNotNull(updatedMarker);
        Assertions.assertEquals("1", String.valueOf(updatedMarker.getId()));
    }

    @Test
    public void should_Delete_Marker_When_Id_Is_Valid() {
        markerService.delete(secondMarker);

        Mockito.verify(markerRepository, Mockito.times(1)).delete(secondMarker);
    }

    private Coordinate createCoordinate(final Long id, final Double longitude, final Double latitude) {
        final Coordinate coordinate = new Coordinate();
        coordinate.setId(id);
        coordinate.setLongitude(longitude);
        coordinate.setLatitude(latitude);
        return coordinate;
    }

    private Marker createMarker(final Long id, final String name, final String description, final LocalDate eventDate, final Integer grade, final Boolean shouldVisitAgain, final Coordinate coordinate) {
        final Marker marker = new Marker();
        marker.setId(id);
        marker.setName(name);
        marker.setDescription(description);
        marker.setEventDate(eventDate);
        marker.setGrade(grade);
        marker.setShouldVisitAgain(shouldVisitAgain);
        marker.setCoordinate(coordinate);
        marker.setModifiedAt(null);
        marker.setUsers(null);
        return marker;
    }

    private UserGetDto createUserGetDto(final Long id, final String firstName, final String lastName, final String email, final String username) {
        final UserGetDto userGetDto = new UserGetDto();
        userGetDto.setId(id);
        userGetDto.setFirstName(firstName);
        userGetDto.setLastName(lastName);
        userGetDto.setEmail(email);
        userGetDto.setUsername(username);
        return userGetDto;
    }

    private MarkerPostDto createMarkerPostDto(final String name, final String description, final LocalDate eventDate, final Integer grade, final Boolean shouldVisitAgain, final Coordinate coordinate) {
        final MarkerPostDto markerPostDto = new MarkerPostDto();
        markerPostDto.setName(name);
        markerPostDto.setDescription(description);
        markerPostDto.setEventDate(eventDate);
        markerPostDto.setGrade(grade);
        markerPostDto.setShouldVisitAgain(shouldVisitAgain);
        markerPostDto.setCoordinate(coordinate);
        markerPostDto.setCreatedAt(LocalDateTime.now());
        return markerPostDto;
    }

    private MarkerPutDto createMarkerPutDto(final Long id, final String name, final String description, final LocalDate eventDate, final Integer grade, final Boolean shouldVisitAgain, final Coordinate coordinate) {
        final MarkerPutDto markerPutDto = new MarkerPutDto();
        markerPutDto.setId(id);
        markerPutDto.setName(name);
        markerPutDto.setDescription(description);
        markerPutDto.setEventDate(eventDate);
        markerPutDto.setGrade(grade);
        markerPutDto.setShouldVisitAgain(shouldVisitAgain);
        markerPutDto.setCoordinate(coordinate);
        markerPutDto.setModifiedAt(LocalDateTime.now());
        return markerPutDto;
    }
}
