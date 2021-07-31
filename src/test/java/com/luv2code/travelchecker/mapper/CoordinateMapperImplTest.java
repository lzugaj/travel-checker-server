package com.luv2code.travelchecker.mapper;

import com.luv2code.travelchecker.domain.Coordinate;
import com.luv2code.travelchecker.dto.coordinate.CoordinateGetDto;
import com.luv2code.travelchecker.mapper.impl.CoordinateMapperImpl;
import com.luv2code.travelchecker.utils.CoordinateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class CoordinateMapperImplTest {

    @InjectMocks
    private CoordinateMapperImpl coordinateMapper;

    @Mock
    private ModelMapper modelMapper;

    private Coordinate firstCoordinate;
    private List<Coordinate> coordinates;

    @BeforeEach
    public void setup() {
        // Coordinate
        firstCoordinate = CoordinateUtil.createCoordinate(1L, 45.232132232, 15.123123123);
        Coordinate secondCoordinate = CoordinateUtil.createCoordinate(2L, 40.111132232, 12.123123123);

        // CoordinateGetDto
        CoordinateGetDto firstCoordinateGetDto = CoordinateUtil.createCoordinateGetDto(firstCoordinate.getId(), firstCoordinate.getLongitude(), firstCoordinate.getLatitude());
        CoordinateGetDto secondCoordinateGetDto = CoordinateUtil.createCoordinateGetDto(secondCoordinate.getId(), secondCoordinate.getLongitude(), secondCoordinate.getLatitude());

        coordinates = Arrays.asList(firstCoordinate, secondCoordinate);

        Mockito.when(modelMapper.map(firstCoordinate, CoordinateGetDto.class)).thenReturn(firstCoordinateGetDto);
        Mockito.when(modelMapper.map(secondCoordinate, CoordinateGetDto.class)).thenReturn(secondCoordinateGetDto);
    }

    @Test
    @DisplayName("entityToDto(Coordinate) - should return CoordinateGetDto")
    public void should_Return_CoordinateGetDto_When_Correctly_Mapped() {
        final CoordinateGetDto searchedDtoCoordinate = coordinateMapper.entityToDto(firstCoordinate);

        Assertions.assertNotNull(searchedDtoCoordinate);
        Assertions.assertEquals("1", String.valueOf(searchedDtoCoordinate.getId()));
    }

    @Test
    @DisplayName("entitiesToDto(List<Coordinate>) - should return CoordinateGetDto list")
    public void should_Return_CoordinateGetDto_List_When_Correctly_Mapped() {
        final List<CoordinateGetDto> searchedDtoCoordinates = coordinateMapper.entitiesToDto(coordinates);

        Assertions.assertNotNull(searchedDtoCoordinates);
        Assertions.assertEquals(2, searchedDtoCoordinates.size());
    }
}
