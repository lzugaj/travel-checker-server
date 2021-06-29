package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.dto.coordinate.CoordinateGetDto;

import java.util.List;

public interface CoordinateService {

    CoordinateGetDto findById(final Long id);

    List<CoordinateGetDto> findAll();

}
