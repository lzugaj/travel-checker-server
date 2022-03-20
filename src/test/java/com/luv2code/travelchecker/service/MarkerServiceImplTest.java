package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.domain.Coordinate;
import com.luv2code.travelchecker.domain.Marker;
import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.exception.EntityNotFoundException;
import com.luv2code.travelchecker.mock.CoordinateMock;
import com.luv2code.travelchecker.mock.MarkerMock;
import com.luv2code.travelchecker.mock.UserMock;
import com.luv2code.travelchecker.repository.MarkerRepository;
import com.luv2code.travelchecker.service.impl.MarkerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class MarkerServiceImplTest {

    @InjectMocks
    private MarkerServiceImpl markerService;

    @Mock
    private MarkerRepository markerRepository;

    private Marker firstMarker;
    private Marker secondMarker;
    private Marker thirdMarker;
    private Marker fourthMarker;

    private User user;

    @BeforeEach
    public void setup() {
        // Coordinate
        final Coordinate firstCoordinate = CoordinateMock.createCoordinate(15.966568111, 45.815399222);
        final Coordinate secondCoordinate = CoordinateMock.createCoordinate( 15.242222412, 44.119722345);
        final Coordinate thirdCoordinate = CoordinateMock.createCoordinate(18.092162345, 42.648071234);

        // Marker
        firstMarker = MarkerMock.createMarker(UUID.fromString("30b8d54a-9dfc-11ec-b909-0242ac120002"), "Zagreb", "Zagreb was beautiful destination", LocalDate.of(2021, 3, 11), 4, Boolean.TRUE, LocalDateTime.now(), firstCoordinate);
        secondMarker = MarkerMock.createMarker(UUID.fromString("57a25226-9dfc-11ec-b909-0242ac120002"), "Split", "Split is nice but there are too much tourists", LocalDate.of(2020, 8, 1), 3, Boolean.FALSE, LocalDateTime.now(), secondCoordinate);
        thirdMarker = MarkerMock.createMarker(UUID.randomUUID(), "Dubrovnik", "Dubrovnik is like a paradise", (LocalDate.of(2020, 8, 1)), 5, Boolean.TRUE, LocalDateTime.now(), thirdCoordinate);
        fourthMarker = MarkerMock.createMarker(UUID.fromString("30b8d54a-9dfc-11ec-b909-0242ac120002"), thirdMarker.getName(), thirdMarker.getDescription(), firstMarker.getEventDate(), secondMarker.getGrade(), firstMarker.getShouldVisitAgain(), LocalDateTime.now(), firstMarker.getCoordinate());

        final List<Marker> markers = Arrays.asList(secondMarker, thirdMarker);

        // UserGetDto
        user = UserMock.createUser(UUID.randomUUID(), "Eunice", "Holt", "eholt@gmail.com", "Mone1968");

        BDDMockito.given(markerRepository.save(firstMarker)).willReturn(firstMarker);
        BDDMockito.given(markerRepository.save(fourthMarker)).willReturn(fourthMarker);
        BDDMockito.given(markerRepository.findById(secondMarker.getId())).willReturn(java.util.Optional.ofNullable(secondMarker));
        BDDMockito.given(markerRepository.findAll()).willReturn(markers);
    }

    @Test
    public void should_Save_Marker_When_Everything_Is_Entered_Correctly() {
        final Marker newMarker = markerService.save(user, firstMarker);

        Assertions.assertNotNull(newMarker);
        Assertions.assertEquals("30b8d54a-9dfc-11ec-b909-0242ac120002", String.valueOf(newMarker.getId()));
    }

    @Test
    public void should_Return_Marker_When_Id_Exists() {
        final Marker searchedMaker = markerService.findById(secondMarker.getId());

        Assertions.assertNotNull(searchedMaker);
        Assertions.assertEquals("57a25226-9dfc-11ec-b909-0242ac120002", String.valueOf(searchedMaker.getId()));
    }

    @Test
    public void should_Throw_Exception_When_Id_Not_Founded() {
        BDDMockito.given(markerRepository.findById(firstMarker.getId()))
                .willReturn(Optional.empty());

        final Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> markerService.findById(firstMarker.getId())
        );

        final String expectedMessage = String.format("Cannot find searched Marker by given id. [id=%s]", firstMarker.getId());
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
        final Marker updatedMarker = markerService.update(fourthMarker);

        Assertions.assertNotNull(updatedMarker);
        Assertions.assertEquals("30b8d54a-9dfc-11ec-b909-0242ac120002", String.valueOf(updatedMarker.getId()));
        Assertions.assertEquals("Dubrovnik", updatedMarker.getName());
        Assertions.assertEquals("Dubrovnik is like a paradise", updatedMarker.getDescription());
    }

    @Test
    public void should_Delete_Marker_When_Id_Is_Exists() {
        markerService.delete(secondMarker);

        Mockito.verify(markerRepository, Mockito.times(1)).delete(secondMarker);
    }
}
