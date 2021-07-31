package com.luv2code.travelchecker.service.impl;

import com.luv2code.travelchecker.domain.Marker;
import com.luv2code.travelchecker.domain.User;
import com.luv2code.travelchecker.exception.EntityNotFoundException;
import com.luv2code.travelchecker.repository.MarkerRepository;
import com.luv2code.travelchecker.service.CoordinateService;
import com.luv2code.travelchecker.service.MarkerService;
import com.luv2code.travelchecker.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarkerServiceImpl implements MarkerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MarkerServiceImpl.class);

    private final MarkerRepository markerRepository;

    private final CoordinateService coordinateService;

    private final UserService userService;

    @Autowired
    public MarkerServiceImpl(final MarkerRepository markerRepository,
                             final CoordinateService coordinateService,
                             final UserService userService) {
        this.markerRepository = markerRepository;
        this.coordinateService = coordinateService;
        this.userService = userService;
    }

    @Override
    public Marker save(final User user, final Marker marker) {
        coordinateService.save(marker.getCoordinate());
        final Marker newMarker = markerRepository.save(marker);
        LOGGER.info("Creating new Marker with id: ´{}´.", marker.getId());

        return newMarker;
    }

    @Override
    public Marker findById(final Long id) {
        LOGGER.info("Searching Marker with id: ´{}´.", id);
        return markerRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Cannot find Marker with id: ´{}´.", id);
                    return new EntityNotFoundException("Marker", "id", String.valueOf(id));
                });
    }

    @Override
    public List<Marker> findAll() {
        LOGGER.info("Searching all Markers.");
        return markerRepository.findAll();
    }

    @Override
    public Marker update(final Marker marker) {
        LOGGER.info("Updating Marker with id: ´{}´.", marker.getId());
        return markerRepository.save(marker);
    }

    @Override
    public void delete(final Marker marker) {
        LOGGER.info("Deleting Marker with id: ´{}´.", marker.getId());
        markerRepository.delete(marker);
    }
}
