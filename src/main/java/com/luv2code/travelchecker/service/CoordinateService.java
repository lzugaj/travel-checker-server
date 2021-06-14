package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.domain.Coordinate;

import java.util.List;

public interface CoordinateService {

    Coordinate findById(final Long id);

    Coordinate findByCoordinates(final Double longitude, final Double latitude);

    List<Coordinate> findAll();

}
