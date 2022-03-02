package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.domain.Coordinate;

import java.util.List;
import java.util.UUID;

public interface CoordinateService {

    Coordinate findById(final UUID id);

    List<Coordinate> findAll();

}
