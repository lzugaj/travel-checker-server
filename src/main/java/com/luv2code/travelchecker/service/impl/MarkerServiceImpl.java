package com.luv2code.travelchecker.service.impl;

import com.luv2code.travelchecker.domain.Marker;
import com.luv2code.travelchecker.dto.marker.MarkerPostDto;
import com.luv2code.travelchecker.dto.marker.MarkerPutDto;
import com.luv2code.travelchecker.dto.user.UserGetDto;
import com.luv2code.travelchecker.exception.EntityNotFoundException;
import com.luv2code.travelchecker.mapper.MarkerMapper;
import com.luv2code.travelchecker.repository.MarkerRepository;
import com.luv2code.travelchecker.service.MarkerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarkerServiceImpl implements MarkerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MarkerServiceImpl.class);

    private final MarkerRepository markerRepository;

    private final MarkerMapper markerMapper;

    @Autowired
    public MarkerServiceImpl(final MarkerRepository markerRepository,
                             final MarkerMapper markerMapper) {
        this.markerRepository = markerRepository;
        this.markerMapper = markerMapper;
    }

    @Override
    public Marker save(final UserGetDto userGetDto, final MarkerPostDto markerPostDto) {
        final Marker marker = markerRepository.save(markerMapper.dtoToEntity(markerPostDto));
        LOGGER.info("Creating new Marker with id: ´{}´.", marker.getId());
        return marker;
    }

    @Override
    public Marker findById(final Long id) {
        final Optional<Marker> searchedMarker = markerRepository.findById(id);
        if (searchedMarker.isPresent()) {
            LOGGER.info("Searching Marker with id: ´{}´.", id);
            return searchedMarker.get();
        } else {
            LOGGER.error("Cannot find Marker with id: ´{}´.", id);
            throw new EntityNotFoundException("Marker", "id", String.valueOf(id));
        }
    }

    @Override
    public List<Marker> findAll() {
        final List<Marker> searchedMarkers = markerRepository.findAll();
        LOGGER.info("Searching all Markers.");
        return searchedMarkers;
    }

    @Override
    public Marker update(final Marker oldMarker, final MarkerPutDto markerPutDto) {
        final Marker updatedMarker = markerRepository.save(markerMapper.dtoToEntity(oldMarker, markerPutDto));
        LOGGER.info("Updating Marker with id: ´{}´.", oldMarker.getId());
        return updatedMarker;
    }

    @Override
    public void delete(final Marker marker) {
        LOGGER.info("Deleting Marker with id: ´{}´.", marker.getId());
        markerRepository.delete(marker);
    }
}
