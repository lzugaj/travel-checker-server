package com.luv2code.travelchecker.service;

import com.luv2code.travelchecker.domain.Marker;
import com.luv2code.travelchecker.dto.marker.MarkerGetDto;
import com.luv2code.travelchecker.dto.marker.MarkerPostDto;
import com.luv2code.travelchecker.dto.user.UserGetDto;

import java.util.List;

public interface MarkerService {

    MarkerGetDto save(final UserGetDto userGetDto, final MarkerPostDto markerPostDto);

    MarkerGetDto findById(final Long id);

    List<MarkerGetDto> findAll();

//    Marker update(final Marker oldMarker, final MarkerPutDto markerPutDto);

    void delete(final Marker marker);

}
