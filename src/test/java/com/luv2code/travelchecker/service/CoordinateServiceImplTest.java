package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.domain.Coordinate;
import com.luv2code.travelchecker.exception.EntityNotFoundException;
import com.luv2code.travelchecker.repository.CoordinateRepository;
import com.luv2code.travelchecker.service.impl.CoordinateServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CoordinateServiceImplTest {

    @InjectMocks
    private CoordinateServiceImpl coordinateService;

    @Mock
    private CoordinateRepository coordinateRepository;

    private Coordinate firstCoordinate;
    private Coordinate secondCoordinate;
    private Coordinate thirdCoordinate;

    @BeforeEach
    public void setup() {
        firstCoordinate = new Coordinate();
        firstCoordinate.setId(1L);
        firstCoordinate.setLongitude(45.232132232);
        firstCoordinate.setLatitude(15.123123123);

        secondCoordinate = new Coordinate();
        secondCoordinate.setId(2L);
        secondCoordinate.setLongitude(32.876543211);
        secondCoordinate.setLatitude(18.123456789);

        thirdCoordinate = new Coordinate();
        thirdCoordinate.setId(3L);
        thirdCoordinate.setLongitude(49.888777333);
        thirdCoordinate.setLatitude(21.762123456);

        final List<Coordinate> coordinates = new ArrayList<>();
        coordinates.add(firstCoordinate);
        coordinates.add(secondCoordinate);
        coordinates.add(thirdCoordinate);

        Mockito.when(coordinateRepository.findById(firstCoordinate.getId())).thenReturn(java.util.Optional.ofNullable(firstCoordinate));
        Mockito.when(coordinateRepository.findCoordinateByLongitudeAndLatitude(secondCoordinate.getLongitude(), secondCoordinate.getLatitude())).thenReturn(java.util.Optional.ofNullable(secondCoordinate));
        Mockito.when(coordinateRepository.findAll()).thenReturn(coordinates);
    }

    @Test
    public void should_Return_Coordinate_When_Id_Is_Valid() {
        final Coordinate searchedCoordinate = coordinateService.findById(firstCoordinate.getId());

        Assertions.assertNotNull(searchedCoordinate);
        Assertions.assertEquals("1", String.valueOf(searchedCoordinate.getId()));
    }

    @Test
    public void should_Throw_Exception_When_Id_Is_Not_Valid() {
        Mockito.when(coordinateRepository.findById(thirdCoordinate.getId()))
                .thenReturn(Optional.empty());

        final Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> coordinateService.findById(thirdCoordinate.getId()));

        final String expectedMessage = "Entity 'Coordinate' with 'id' value '3' not founded.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void should_Return_Coordinate_When_Longitude_Latitudes_Are_Valid() {
        final Coordinate searchedCoordinate = coordinateService.findByCoordinates(secondCoordinate.getLongitude(), secondCoordinate.getLatitude());

        Assertions.assertNotNull(searchedCoordinate);
        Assertions.assertEquals("32.876543211", String.valueOf(searchedCoordinate.getLongitude()));
        Assertions.assertEquals("18.123456789", String.valueOf(searchedCoordinate.getLatitude()));
    }

    @Test
    public void should_Throw_Exception_When_Longitude_Latitudes_Are_Not_Valid() {
        Mockito.when(coordinateRepository.findCoordinateByLongitudeAndLatitude(thirdCoordinate.getLongitude(), thirdCoordinate.getLatitude()))
                .thenReturn(Optional.empty());

        final Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> coordinateService.findByCoordinates(thirdCoordinate.getLongitude(), thirdCoordinate.getLatitude()));

        final String expectedMessage = "Entity 'Coordinate' with 'longitude and latitude' value '49.888777333, 21.762123456' not founded.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void should_Return_All_Coordinates() {
        final List<Coordinate> coordinates = coordinateService.findAll();

        Assertions.assertNotNull(coordinates);
        Assertions.assertEquals(3, coordinates.size());
    }
}
