package com.luv2code.travelchecker.service.impl;

import com.luv2code.travelchecker.domain.Coordinate;
import com.luv2code.travelchecker.repository.CoordinateRepository;
import com.luv2code.travelchecker.service.CoordinateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CoordinateServiceImpl extends AbstractEntityServiceImpl<Coordinate, CoordinateRepository> implements CoordinateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoordinateServiceImpl.class);

    @Autowired
    public CoordinateServiceImpl(final CoordinateRepository coordinateRepository) {
        super(coordinateRepository, Coordinate.class);
    }

    @Override
    public Coordinate findById(final UUID id) {
        LOGGER.debug("Searching Coordinate by given id. [id={}]", id);
        return super.findById(id);
    }

    @Override
    public List<Coordinate> findAll() {
        LOGGER.debug("Searching all Coordinates.");
        return super.findAll();
    }
}
