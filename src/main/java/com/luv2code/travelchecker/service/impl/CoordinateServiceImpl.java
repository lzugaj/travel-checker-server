package com.luv2code.travelchecker.service.impl;

import com.luv2code.travelchecker.domain.Coordinate;
import com.luv2code.travelchecker.exception.EntityNotFoundException;
import com.luv2code.travelchecker.repository.CoordinateRepository;
import com.luv2code.travelchecker.service.CoordinateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoordinateServiceImpl implements CoordinateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoordinateServiceImpl.class);

    private final CoordinateRepository coordinateRepository;

    @Autowired
    public CoordinateServiceImpl(final CoordinateRepository coordinateRepository) {
        this.coordinateRepository = coordinateRepository;
    }

    @Override
    public Coordinate save(final Coordinate coordinate) {
        return coordinateRepository.save(coordinate);
    }

    @Override
    public Coordinate findById(final Long id) {
        LOGGER.info("Searching Coordinate with id: ´{}´.", id);
        return coordinateRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Cannot find Coordinate with id: ´{}´.", id);
                    return new EntityNotFoundException("Coordinate", "id", String.valueOf(id));
                });
    }

    @Override
    public List<Coordinate> findAll() {
        LOGGER.info("Searching all Coordinates.");
        return coordinateRepository.findAll();
    }
}
