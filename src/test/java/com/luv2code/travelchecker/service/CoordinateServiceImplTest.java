package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.domain.Coordinate;
import com.luv2code.travelchecker.exception.EntityNotFoundException;
import com.luv2code.travelchecker.mock.CoordinateMock;
import com.luv2code.travelchecker.repository.CoordinateRepository;
import com.luv2code.travelchecker.service.impl.CoordinateServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
    private Coordinate secondCoordinate;
    private Coordinate thirdCoordinate;

    @BeforeEach
    public void setup() {
        firstCoordinate = CoordinateMock.createCoordinate(45.232132232, 15.123123123);
        secondCoordinate = CoordinateMock.createCoordinate(32.876543211, 18.123456789);
        thirdCoordinate = CoordinateMock.createCoordinate(49.888777333, 21.762123456);

        final List<Coordinate> coordinates = Arrays.asList(firstCoordinate, secondCoordinate, thirdCoordinate);

        BDDMockito.given(coordinateRepository.findById(firstCoordinate.getId()))
                .willReturn(java.util.Optional.ofNullable(firstCoordinate));
        BDDMockito.given(coordinateRepository.findAll())
                .willReturn(coordinates);
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
        BDDMockito.given(coordinateRepository.findById(thirdCoordinate.getId()))
                .willReturn(Optional.empty());

        final Exception exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> coordinateService.findById(thirdCoordinate.getId())
        );

        final String expectedMessage = String.format("Cannot find searched Coordinate by given id. [id=%s]", thirdCoordinate.getId());
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
