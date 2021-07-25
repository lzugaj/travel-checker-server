package com.luv2code.travelchecker.mapper;

import com.luv2code.travelchecker.domain.Coordinate;
import com.luv2code.travelchecker.dto.coordinate.CoordinateGetDto;
import com.luv2code.travelchecker.mapper.impl.CoordinateMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class CoordinateMapperImplTest {

    @InjectMocks
    private CoordinateMapperImpl coordinateMapper;

    private Coordinate firstCoordinate;
    private Coordinate secondCoordinate;

    private CoordinateGetDto firstCoordinateGetDto;
    private CoordinateGetDto secondCoordinateGetDto;

    private List<Coordinate> coordinates;
    private List<CoordinateGetDto> dtoCoordinates;

    @BeforeEach
    public void setup() {
        // Coordinate
        firstCoordinate = createCoordinate(1L, 45.232132232, 15.123123123);
        secondCoordinate = createCoordinate(2L, 40.111132232, 12.123123123);

        // CoordinateGetDto
        firstCoordinateGetDto = createCoordinateGetDto(firstCoordinate.getLongitude(), firstCoordinate.getLatitude());
        secondCoordinateGetDto = createCoordinateGetDto(secondCoordinate.getLongitude(), secondCoordinate.getLatitude());

        coordinates = Arrays.asList(firstCoordinate, secondCoordinate);
        dtoCoordinates = Arrays.asList(firstCoordinateGetDto, secondCoordinateGetDto);
    }

    @Test
    public void should_Return_CoordinateGetDto_When_Correctly_Mapped() {
        final CoordinateGetDto searchedDtoCoordinate = coordinateMapper.entityToDto(firstCoordinate);

        Assertions.assertNotNull(searchedDtoCoordinate);
        Assertions.assertEquals("45.232132232", String.valueOf(searchedDtoCoordinate.getLongitude()));
        Assertions.assertEquals("15.123123123", String.valueOf(searchedDtoCoordinate.getLatitude()));
    }

    @Test
    public void should_Return_CoordinateGetDto_List_When_Correctly_Mapped() {
        final List<CoordinateGetDto> searchedDtoCoordinates = coordinateMapper.entitiesToDto(coordinates);

        Assertions.assertNotNull(searchedDtoCoordinates);
        Assertions.assertEquals(2, searchedDtoCoordinates.size());
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
}
