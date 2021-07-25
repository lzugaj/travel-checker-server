package com.luv2code.travelchecker.mapper.impl;

import com.luv2code.travelchecker.domain.Marker;
import com.luv2code.travelchecker.dto.coordinate.CoordinateGetDto;
import com.luv2code.travelchecker.dto.marker.MarkerGetDto;
import com.luv2code.travelchecker.dto.marker.MarkerPostDto;
import com.luv2code.travelchecker.dto.marker.MarkerPutDto;
import com.luv2code.travelchecker.mapper.CoordinateMapper;
import com.luv2code.travelchecker.mapper.MarkerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class MarkerMapperImpl implements MarkerMapper {

    public static final Logger LOGGER = LoggerFactory.getLogger(MarkerMapperImpl.class);

    private final CoordinateMapper coordinateMapper;

    @Autowired
    public MarkerMapperImpl(final CoordinateMapper coordinateMapper) {
        this.coordinateMapper = coordinateMapper;
    }

    @Override
    public Marker dtoToEntity(final MarkerPostDto dto) {
        LOGGER.info("Start mapping MarkerPostDto to Marker.");
        final Marker marker = new Marker();
        marker.setName(dto.getName());
        marker.setDescription(dto.getDescription());
        marker.setEventDate(dto.getEventDate());
        marker.setGrade(dto.getGrade());
        marker.setShouldVisitAgain(dto.getShouldVisitAgain());
        marker.setCoordinate(dto.getCoordinate());
        marker.setCreatedAt(dto.getCreatedAt());
        return marker;
    }

    @Override
    public MarkerGetDto entityToDto(final Marker entity) {
        LOGGER.info("Start mapping Marker to MarkerGetDto.");
        final MarkerGetDto searchedDtoMarker = new MarkerGetDto();
        searchedDtoMarker.setId(entity.getId());
        searchedDtoMarker.setName(entity.getName());
        searchedDtoMarker.setDescription(entity.getDescription());
        searchedDtoMarker.setEventDate(entity.getEventDate());
        searchedDtoMarker.setGrade(entity.getGrade());
        searchedDtoMarker.setShouldVisitAgain(entity.getShouldVisitAgain());

        final CoordinateGetDto searchedDtoCoordinate = coordinateMapper.entityToDto(entity.getCoordinate());
        searchedDtoMarker.setCoordinate(searchedDtoCoordinate);
        return searchedDtoMarker;
    }

    @Override
    public Marker dtoToEntity(final Marker entity, final MarkerPutDto dto) {
        LOGGER.info("Start mapping MarkerPutDto to Marker.");
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setEventDate(dto.getEventDate());
        entity.setGrade(dto.getGrade());
        entity.setShouldVisitAgain(dto.getShouldVisitAgain());
        entity.setCoordinate(dto.getCoordinate());
        entity.setModifiedAt(LocalDateTime.now());
        return entity;
    }

    @Override
    public List<MarkerGetDto> entitiesToDto(final List<Marker> entities) {
        LOGGER.info("Start mapping Marker to MarkerGetDto list.");
        final List<MarkerGetDto> searchedDtoMarkers = new ArrayList<>();
        for (Marker marker : entities) {
            searchedDtoMarkers.add(entityToDto(marker));
        }

        return searchedDtoMarkers;
    }
}
