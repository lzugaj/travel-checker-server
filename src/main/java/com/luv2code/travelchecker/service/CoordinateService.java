package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.domain.Coordinate;

import java.util.List;

public interface CoordinateService {

    Coordinate save(final Coordinate coordinate);

    Coordinate findById(final Long id);

    List<Coordinate> findAll();

}
