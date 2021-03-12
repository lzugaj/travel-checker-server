package com.luv2code.travelchecker.service.impl;

import com.luv2code.travelchecker.domain.Marker;
import com.luv2code.travelchecker.exception.EntityAlreadyExistsException;
import com.luv2code.travelchecker.exception.EntityNotFoundException;
import com.luv2code.travelchecker.repository.MarkerRepository;
import com.luv2code.travelchecker.service.MarkerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MarkerServiceImpl implements MarkerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MarkerServiceImpl.class);

    private final MarkerRepository markerRepository;

    public MarkerServiceImpl(final MarkerRepository markerRepository) {
        this.markerRepository = markerRepository;
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
    public List<Marker> findAll() {
        final List<Marker> markers = markerRepository.findAll();
        LOGGER.info("Searching all Markers.");
        return markers;
    }

    @Override
    public Marker update(final Marker oldMarker, final Marker newMarker) {
        return null;
    }

    @Override
    public void delete(final Marker marker) {

    }
}
