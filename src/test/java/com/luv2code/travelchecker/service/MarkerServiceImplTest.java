package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.domain.Coordinate;
import com.luv2code.travelchecker.domain.Marker;
import com.luv2code.travelchecker.exception.EntityNotFoundException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class MarkerServiceImplTest {

    @InjectMocks
    private MarkerServiceImpl markerService;

    @Mock
    private MarkerRepository markerRepository;

    @Mock
    private CoordinateService coordinateService;

    private Marker firstMarker;
    private Marker secondMarker;
    private Marker thirdMarker;

    @BeforeEach
    public void setup() {
        final Coordinate zagrebCoordinate = new Coordinate();
        zagrebCoordinate.setId(1L);
        zagrebCoordinate.setLongitude(15.966568111);
        zagrebCoordinate.setLatitude(45.815399222);

        final Coordinate splitCoordinate = new Coordinate();
        splitCoordinate.setId(2L);
        splitCoordinate.setLongitude(15.242222412);
        splitCoordinate.setLatitude(44.119722345);

        final Coordinate dubrovnikCoordinate = new Coordinate();
        dubrovnikCoordinate.setId(3L);
        dubrovnikCoordinate.setLongitude(18.092162345);
        dubrovnikCoordinate.setLatitude(42.648071234);

        firstMarker = new Marker();
        firstMarker.setId(1L);
        firstMarker.setName("Zagreb");
        firstMarker.setDescription("Zagreb was beautiful destination.");
        firstMarker.setEventDate(LocalDate.of(2021, 3, 11));
        firstMarker.setGrade(4);
        firstMarker.setShouldVisitAgain(true);
        firstMarker.setCoordinate(zagrebCoordinate);

        secondMarker = new Marker();
        secondMarker.setId(2L);
        secondMarker.setName("Split");
        secondMarker.setDescription("Split is nice but there is too much tourists.");
        secondMarker.setEventDate(LocalDate.of(2020, 8, 1));
        secondMarker.setGrade(3);
        secondMarker.setShouldVisitAgain(false);
        secondMarker.setCoordinate(splitCoordinate);

        thirdMarker = new Marker();
        thirdMarker.setId(3L);
        thirdMarker.setName("Dubrovnik");
        thirdMarker.setDescription("Dubrovnik is like a paradise.");
        thirdMarker.setEventDate(LocalDate.of(2020, 10, 15));
        thirdMarker.setGrade(5);
        thirdMarker.setShouldVisitAgain(true);
        thirdMarker.setCoordinate(dubrovnikCoordinate);

//        zagrebCoordinate.setMarker(firstMarker);
//        splitCoordinate.setMarker(secondMarker);
//        dubrovnikCoordinate.setMarker(thirdMarker);

        List<Marker> markers = new ArrayList<>();
        markers.add(secondMarker);
        markers.add(thirdMarker);

        Mockito.when(markerRepository.save(firstMarker)).thenReturn(firstMarker);
        Mockito.when(markerRepository.findById(secondMarker.getId())).thenReturn(java.util.Optional.ofNullable(secondMarker));
        Mockito.when(markerRepository.findAll()).thenReturn(markers);
    }

//    @Test
//    public void should_Save_Marker_When_Name_Not_Exists() {
//        final Marker newMarker = markerService.save(firstMarker);
//
//        Assertions.assertNotNull(newMarker);
//        Assertions.assertEquals("1", String.valueOf(newMarker.getId()));
//    }

//    @Test
//    public void should_Throw_Exception_When_Name_Already_Exists() {
//        Mockito.when(markerRepository.save(secondMarker))
//                .thenThrow(new EntityAlreadyExistsException(
//                        "Marker",
//                        "name",
//                        secondMarker.getName()));
//
//        final Exception exception = Assertions.assertThrows(
//                EntityAlreadyExistsException.class,
//                () -> markerService.save(secondMarker));
//
//        final String expectedMessage = "Entity 'Marker' with 'name' value 'Split' already exists.";
//        final String actualMessage = exception.getMessage();
//
//        Assertions.assertEquals(expectedMessage, actualMessage);
//    }

//    @Test
//    public void should_Return_Marker_When_Id_Is_Valid() {
//        final Marker searchedMaker = markerService.findById(secondMarker.getId());
//
//        Assertions.assertNotNull(searchedMaker);
//        Assertions.assertEquals("2", String.valueOf(searchedMaker.getId()));
//    }

    @Test
    public void should_Throw_Exception_When_Id_Not_Founded() {
        Mockito.when(markerRepository.findById(thirdMarker.getId()))
                .thenReturn(Optional.empty());

        final Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> markerService.findById(thirdMarker.getId()));

        final String expectedMessage = "Entity 'Marker' with 'id' value '3' not founded.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

//    @Test
//    public void should_Return_All_Markers() {
//        final List<Marker> markers = markerService.findAll();
//
//        Assertions.assertNotNull(markers);
//        Assertions.assertEquals(2, markers.size());
//    }

    @Test
    public void should_Delete_Marker_When_Id_Is_Valid() {
        markerService.delete(secondMarker);

        Mockito.verify(markerRepository, Mockito.times(1)).delete(secondMarker);
    }
}
