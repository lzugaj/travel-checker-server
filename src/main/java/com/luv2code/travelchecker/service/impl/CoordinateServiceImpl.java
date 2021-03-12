package com.luv2code.travelchecker.service.impl;

import com.luv2code.travelchecker.domain.Coordinate;
import com.luv2code.travelchecker.exception.EntityNotFoundException;
import com.luv2code.travelchecker.repository.CoordinateRepository;
import com.luv2code.travelchecker.service.CoordinateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoordinateServiceImpl implements CoordinateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoordinateServiceImpl.class);

    private final CoordinateRepository coordinateRepository;

    public CoordinateServiceImpl(final CoordinateRepository coordinateRepository) {
        this.coordinateRepository = coordinateRepository;
    }

    @Override
    public Coordinate findById(final Long id) {
        final Optional<Coordinate> coordinate = coordinateRepository.findById(id);
        if (coordinate.isPresent()) {
            LOGGER.info("Searching Coordinate with id: ´{}´.", id);
            return coordinate.get();
        } else {
            LOGGER.error("Cannot find Coordinate with id: ´{}´.", id);
            throw new EntityNotFoundException("Coordinate", "id", String.valueOf(id));
        }
    }

    @Override
    public List<Coordinate> findAll() {
        final List<Coordinate> coordinates = coordinateRepository.findAll();
        LOGGER.info("Searching all Coordinates.");
        return coordinates;
    }
}
