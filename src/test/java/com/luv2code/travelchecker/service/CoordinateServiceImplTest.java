package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.domain.Coordinate;
import com.luv2code.travelchecker.dto.coordinate.CoordinateGetDto;
import com.luv2code.travelchecker.exception.EntityNotFoundException;
import com.luv2code.travelchecker.repository.CoordinateRepository;
import com.luv2code.travelchecker.service.impl.CoordinateServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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

    @Mock
    private ModelMapper modelMapper;

    private Coordinate firstCoordinate;
    private Coordinate secondCoordinate;
    private Coordinate thirdCoordinate;

    @BeforeEach
    public void setup() {
        firstCoordinate = new Coordinate();
        firstCoordinate.setId(1L);
        firstCoordinate.setLongitude(45.232132232);
        firstCoordinate.setLatitude(15.123123123);

        final CoordinateGetDto firstCoordinateGetDto = new CoordinateGetDto();
        firstCoordinateGetDto.setLongitude(firstCoordinate.getLongitude());
        firstCoordinateGetDto.setLatitude(firstCoordinate.getLatitude());

        secondCoordinate = new Coordinate();
        secondCoordinate.setId(2L);
        secondCoordinate.setLongitude(32.876543211);
        secondCoordinate.setLatitude(18.123456789);

        final CoordinateGetDto secondCoordinateGetDto = new CoordinateGetDto();
        secondCoordinateGetDto.setLongitude(secondCoordinate.getLongitude());
        secondCoordinateGetDto.setLatitude(secondCoordinate.getLatitude());

        thirdCoordinate = new Coordinate();
        thirdCoordinate.setId(3L);
        thirdCoordinate.setLongitude(49.888777333);
        thirdCoordinate.setLatitude(21.762123456);

        final CoordinateGetDto thirdCoordinateGetDto = new CoordinateGetDto();
        thirdCoordinateGetDto.setLongitude(thirdCoordinate.getLongitude());
        thirdCoordinateGetDto.setLatitude(thirdCoordinate.getLatitude());

        final List<Coordinate> coordinates = new ArrayList<>();
        coordinates.add(firstCoordinate);
        coordinates.add(secondCoordinate);
        coordinates.add(thirdCoordinate);

        final TypeToken<List<CoordinateGetDto>> typeToken = new TypeToken<>() {};

        final List<CoordinateGetDto> dtoCoordinates = new ArrayList<>();
        dtoCoordinates.add(firstCoordinateGetDto);
        dtoCoordinates.add(secondCoordinateGetDto);
        dtoCoordinates.add(thirdCoordinateGetDto);

        Mockito.when(modelMapper.map(firstCoordinate, CoordinateGetDto.class)).thenReturn(firstCoordinateGetDto);
        Mockito.when(modelMapper.map(coordinates, typeToken.getType())).thenReturn(dtoCoordinates);

        Mockito.when(coordinateRepository.findById(firstCoordinate.getId())).thenReturn(java.util.Optional.ofNullable(firstCoordinate));
        Mockito.when(coordinateRepository.findAll()).thenReturn(coordinates);
    }

    @Test
    public void should_Return_Coordinate_When_Id_Is_Valid() {
        final CoordinateGetDto searchedCoordinate = coordinateService.findById(firstCoordinate.getId());

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
                () -> coordinateService.findById(thirdCoordinate.getId()));

        final String expectedMessage = "Entity 'Coordinate' with 'id' value '3' not founded.";
        final String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void should_Return_All_Coordinates() {
        final List<CoordinateGetDto> coordinates = coordinateService.findAll();

        Assertions.assertNotNull(coordinates);
        Assertions.assertEquals(3, coordinates.size());
    }
}
