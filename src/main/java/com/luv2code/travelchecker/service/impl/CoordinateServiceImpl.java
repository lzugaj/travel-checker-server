package com.luv2code.travelchecker.service.impl;

import com.luv2code.travelchecker.domain.Coordinate;
import com.luv2code.travelchecker.repository.CoordinateRepository;
import com.luv2code.travelchecker.service.CoordinateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CoordinateServiceImpl extends AbstractEntityServiceImpl<Coordinate, CoordinateRepository> implements CoordinateService {

    @Autowired
    public CoordinateServiceImpl(final CoordinateRepository coordinateRepository) {
        super(coordinateRepository, Coordinate.class);
    }

    @Override
    public Coordinate findById(final UUID id) {
        return super.findById(id);
    }

    @Override
    public List<Coordinate> findAll() {
        return super.findAll();
    }
}
