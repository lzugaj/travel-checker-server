package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.domain.Coordinate;
import com.luv2code.travelchecker.domain.Marker;
import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.dto.coordinate.CoordinateGetDto;
import com.luv2code.travelchecker.dto.marker.MarkerPostDto;
import com.luv2code.travelchecker.dto.marker.MarkerPutDto;
import com.luv2code.travelchecker.exception.EntityNotFoundException;
import com.luv2code.travelchecker.repository.MarkerRepository;
import com.luv2code.travelchecker.service.impl.MarkerServiceImpl;
import com.luv2code.travelchecker.utils.CoordinateUtil;
import com.luv2code.travelchecker.utils.MarkerUtil;
import com.luv2code.travelchecker.utils.UserUtil;
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

    private Marker firstMarker;
    private Marker secondMarker;
    private Marker fourthMarker;

    private User user;

    @BeforeEach
    public void setup() {
        // Coordinate
        Coordinate firstCoordinate = CoordinateUtil.createCoordinate(1L, 15.966568111, 45.815399222);
        Coordinate secondCoordinate = CoordinateUtil.createCoordinate(2L, 15.242222412, 44.119722345);
        Coordinate thirdCoordinate = CoordinateUtil.createCoordinate(3L, 18.092162345, 42.648071234);

        // Marker
        firstMarker = MarkerUtil.createMarker(1L, "Zagreb", "Zagreb was beautiful destination", LocalDate.of(2021, 3, 11), 4, Boolean.TRUE, LocalDateTime.now(), firstCoordinate);
        secondMarker = MarkerUtil.createMarker(2L, "Split", "Split is nice but there are too much tourists", LocalDate.of(2020, 8, 1), 3, Boolean.FALSE, LocalDateTime.now(), secondCoordinate);
        Marker thirdMarker = MarkerUtil.createMarker(3L, "Dubrovnik", "Dubrovnik is like a paradise", (LocalDate.of(2020, 8, 1)), 5, Boolean.TRUE, LocalDateTime.now(), thirdCoordinate);
        fourthMarker = MarkerUtil.createMarker(1L, thirdMarker.getName(), thirdMarker.getDescription(), firstMarker.getEventDate(), secondMarker.getGrade(), firstMarker.getShouldVisitAgain(), LocalDateTime.now(), firstMarker.getCoordinate());

        List<Marker> markers = Arrays.asList(secondMarker, thirdMarker);

        // UserGetDto
        user = UserUtil.createUser(1L, "Eunice", "Holt", "eholt@gmail.com", "Mone1968", "#password");

        Mockito.when(markerRepository.save(firstMarker)).thenReturn(firstMarker);
        Mockito.when(markerRepository.save(fourthMarker)).thenReturn(fourthMarker);
        Mockito.when(markerRepository.findById(secondMarker.getId())).thenReturn(java.util.Optional.ofNullable(secondMarker));
        Mockito.when(markerRepository.findAll()).thenReturn(markers);
    }

    @Test
    public void should_Save_Marker_When_Everything_Is_Entered_Correctly() {
        final Marker newMarker = markerService.save(user, firstMarker);

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
        final Marker updatedMarker = markerService.update(fourthMarker);

        Assertions.assertNotNull(updatedMarker);
        Assertions.assertEquals("1", String.valueOf(updatedMarker.getId()));
    }

    @Test
    public void should_Delete_Marker_When_Id_Is_Valid() {
        markerService.delete(secondMarker);

        Mockito.verify(markerRepository, Mockito.times(1)).delete(secondMarker);
    }
}
