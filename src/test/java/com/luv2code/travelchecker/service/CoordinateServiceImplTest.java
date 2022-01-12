package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.domain.Coordinate;
import com.luv2code.travelchecker.exception.EntityNotFoundException;
import com.luv2code.travelchecker.repository.CoordinateRepository;
import com.luv2code.travelchecker.service.impl.CoordinateServiceImpl;
import com.luv2code.travelchecker.util.CoordinateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CoordinateServiceImplTest {

    @InjectMocks
    private CoordinateServiceImpl coordinateService;

    @Mock
    private CoordinateRepository coordinateRepository;

    private Coordinate firstCoordinate;
    private Coordinate thirdCoordinate;

    @BeforeEach
    public void setup() {
        firstCoordinate = CoordinateUtil.createCoordinate(1L, 45.232132232, 15.123123123);
        Coordinate secondCoordinate = CoordinateUtil.createCoordinate(2L, 32.876543211, 18.123456789);
        thirdCoordinate = CoordinateUtil.createCoordinate(3L, 49.888777333, 21.762123456);

        final List<Coordinate> coordinates = Arrays.asList(firstCoordinate, secondCoordinate, thirdCoordinate);

        Mockito.when(coordinateRepository.findById(firstCoordinate.getId())).thenReturn(java.util.Optional.ofNullable(firstCoordinate));
        Mockito.when(coordinateRepository.findAll()).thenReturn(coordinates);
    }

    @Test
    public void should_Return_Coordinate_When_Id_Is_Valid() {
        final Coordinate searchedCoordinate = coordinateService.findById(firstCoordinate.getId());

        Assertions.assertNotNull(searchedCoordinate);
        Assertions.assertEquals("45.232132232", String.valueOf(searchedCoordinate.getLongitude()));
        Assertions.assertEquals("15.123123123", String.valueOf(searchedCoordinate.getLatitude()));
    }

    @Test
    public void should_Throw_Exception_When_Id_Is_Not_Valid() {
        Mockito.when(coordinateRepository.findById(thirdCoordinate.getId()))
                .thenReturn(Optional.empty());

        final Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> coordinateService.findById(thirdCoordinate.getId())
        );

        final String expectedMessage = "Coordinate with id: 3 was not found.";
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
