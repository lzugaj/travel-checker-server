package com.luv2code.travelchecker.service.impl;

import com.luv2code.travelchecker.domain.Coordinate;
import com.luv2code.travelchecker.repository.CoordinateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoordinateServiceImpl extends AbstractEntityServiceImpl<Coordinate, CoordinateRepository> {

    @Autowired
    public CoordinateServiceImpl(final CoordinateRepository coordinateRepository) {
        super(coordinateRepository, Coordinate.class);
    }

    @Override
    public Coordinate findById(final Long id) {
        return super.findById(id);
    }

    @Override
    public List<Coordinate> findAll() {
        return super.findAll();
    }
}
