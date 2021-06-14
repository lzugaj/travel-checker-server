package com.luv2code.travelchecker.service.impl;

import com.luv2code.travelchecker.domain.Coordinate;
import com.luv2code.travelchecker.domain.Marker;
import com.luv2code.travelchecker.exception.EntityAlreadyExistsException;
import com.luv2code.travelchecker.exception.EntityNotFoundException;
import com.luv2code.travelchecker.repository.MarkerRepository;
import com.luv2code.travelchecker.service.CoordinateService;
import com.luv2code.travelchecker.service.MarkerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MarkerServiceImpl implements MarkerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MarkerServiceImpl.class);

    private final MarkerRepository markerRepository;

    private final CoordinateService coordinateService;

    @Autowired
    public MarkerServiceImpl(final MarkerRepository markerRepository,
                             final CoordinateService coordinateService) {
        this.markerRepository = markerRepository;
        this.coordinateService = coordinateService;
    }

    @Override
    public Marker save(final Marker marker) {
        if (markerNameNotExists(marker.getName())) {
            marker.setCreatedDate(LocalDateTime.now());
            marker.setModifiedDate(null);
            final Marker newMarker = markerRepository.save(marker);
            LOGGER.info("Creating new Marker with id: ´{}´.", marker.getId());

            newMarker.getCoordinate().setMarker(newMarker);

            return newMarker;
        } else {
            LOGGER.error("Marker already exists with name: ´{}´.", marker.getName());
            throw new EntityAlreadyExistsException("Marker", "name", marker.getName());
        }
    }

    private boolean markerNameNotExists(final String name) {
        LOGGER.info("Searching does Marker already exists with name: ´{}´.", name);
        return findAll().stream()
                .noneMatch(marker -> marker.getName().equals(name));
    }

    @Override
    public Marker findById(final Long id) {
        final Optional<Marker> marker = markerRepository.findById(id);
        if (marker.isPresent()) {
            LOGGER.info("Searching Marker with id: ´{}´.", id);
            return marker.get();
        } else {
            LOGGER.error("Cannot find Marker with id: ´{}´.", id);
            throw new EntityNotFoundException("Marker", "id", String.valueOf(id));
        }
    }

    @Override
    public Marker findByCoordinate(final Double longitude, final Double latitude) {
        final Coordinate searchedCoordinate = coordinateService.findByCoordinates(longitude, latitude);
        LOGGER.info("Successfully founded Coordinate with longitude and latitude: {}, {}", longitude, latitude);
        return searchedCoordinate.getMarker();
    }

    @Override
    public List<Marker> findAll() {
        final List<Marker> markers = markerRepository.findAll();
        LOGGER.info("Searching all Markers.");
        return markers;
    }

    // TODO: @lzugaj => Refactor
    @Override
    public Marker update(final Marker oldMarker, final Marker newMarker) {
        updateVariables(oldMarker, newMarker);
        LOGGER.info("Successfully setup variables for Marker with id: ´{}´.", oldMarker.getId());

        markerRepository.save(oldMarker);
        LOGGER.info("Updating Marker with id: ´{}´.", oldMarker.getId());
        return oldMarker;
    }

    // TODO: @lzugaj => Refactor
    private void updateVariables(final Marker oldMarker, final Marker newMarker) {
        LOGGER.info("Setting up variables for Marker with id: ´{}´.", oldMarker.getId());
        oldMarker.setName(newMarker.getName());
        oldMarker.setDescription(newMarker.getDescription());
        oldMarker.setEventDate(newMarker.getEventDate());
        oldMarker.setShouldVisitAgain(newMarker.getShouldVisitAgain());
        oldMarker.setGrade(newMarker.getGrade());
        oldMarker.setCoordinate(newMarker.getCoordinate());
        oldMarker.setModifiedDate(LocalDateTime.now());
    }

    // TODO: @lzugaj => Refactor
    @Override
    public void delete(final Marker marker) {
        LOGGER.info("Deleting Marker with id: ´{}´.", marker.getId());
        markerRepository.delete(marker);
    }
}
